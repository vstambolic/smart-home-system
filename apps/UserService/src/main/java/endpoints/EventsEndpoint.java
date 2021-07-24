package endpoints;

import endpoints.jms_app_ids.AppId;
import entities.Event;
import entities.User;
import utils.HttpHeadersReader;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static endpoints.jms_app_ids.EventManagerService.*;

@Path("events")
@Stateless
public class EventsEndpoint {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Resource(lookup = "smartHomeConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "smartHomeTopic")
    private Topic topic;

    @Resource(lookup = "userServiceTopic")
    private Topic userServiceTopic;

    @GET
    @Path("get_current_event")
    public Response getCurrentEvent(@Context HttpHeaders httpHeaders) {
        User user = HttpHeadersReader.getUser(em, httpHeaders);        // Get user
        em.refresh(user);
        if (user.getCurrEvent() == null)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.OK).entity(user.getCurrEvent()).build();
    }

    @GET
    @Path("get_events")
    public Response getEvents(@Context HttpHeaders httpHeaders) {

        User user = HttpHeadersReader.getUser(em, httpHeaders);        // Get user
        em.refresh(user);

        Object eventList = this.callEventManagerService(FETCH_ALL, 0, user.getIdUser());
        if (eventList == null)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.OK).entity(new GenericEntity<List<Event>>((List<Event>)eventList){}).build();

//        return Response.status(Response.Status.OK).entity(new GenericEntity<List<Event>>(user.getEventList()) {
//        }).build();

    }

    @POST
    @Path("post_event")
    public Response postEvent(@Context HttpHeaders httpHeaders, @FormParam("label") String label,
                              @FormParam("date") String date, @FormParam("time") String time,
                              @FormParam("address") String address, @FormParam("latitude") double latitude,
                              @FormParam("ringtone") String spotifyURI, @FormParam("longitude") double longitude) {

        User user = HttpHeadersReader.getUser(em, httpHeaders);        // Get user

        Event event = new Event();
        event.setIdEvent(em.createQuery("SELECT COALESCE(MAX(e.idEvent), 0) FROM Event e", Long.class).getResultList().get(0).intValue() + 1);
        event.setLabel(label);
        event.setIdUser(user);
        event.setAddress(address);
        event.setLatitude(latitude);
        event.setLongitude(longitude);

        try {
            event.setTime(new SimpleDateFormat("HH:mm").parse(time));
            event.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        }
        catch (ParseException e) {
        }
        em.persist(event);
        em.flush();

        AlarmsEndpoint alarmsEndpoint = new AlarmsEndpoint();
        alarmsEndpoint.setEM(this.em);
        alarmsEndpoint.setConnectionFactory(connectionFactory);
        alarmsEndpoint.setTopic(topic);
        alarmsEndpoint.postAlarm(httpHeaders, label, time, date, (short) 0, event.getIdEvent(), spotifyURI);

        return Response.ok(event).build();
    }


    private Object callEventManagerService(int serviceID, int eventID, int userID) {
        String timestamp = String.valueOf(System.currentTimeMillis());

        class ProducerThread extends Thread {

             private final int eventID;
             private final int serviceID;
             private final int userID;


             public ProducerThread(int serviceID, int eventID, int userID) {
                 this.serviceID=serviceID;
                 this.eventID=eventID;
                 this.userID = userID;
             }

            @Override
            public void run() {
                JMSContext context = connectionFactory.createContext();       // Create message
                JMSProducer producer = context.createProducer();

                Message message = context.createObjectMessage(this.userID);

                int appID = AppId.EVENT_MANAGER.ordinal();                      // Set message properties
                int serviceID = this.serviceID;

                try {
                    message.setIntProperty("appID", appID);
                    message.setIntProperty("serviceID", serviceID);
                    message.setStringProperty("timestamp", timestamp);

                } catch (JMSException e) {
                    e.printStackTrace();
                }

                producer.send(topic, message);                                 // Send message
            }
        }
        class ConsumerThread extends Thread {
            private Object object;

            public Object getObject() {
                return object;
            }

            @Override
            public void run() {
                JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(userServiceTopic, "timestamp=" + "'" + timestamp + "'");     // Wait for jms response
                ObjectMessage responseMessage = (ObjectMessage) consumer.receive();
                try {
                    this.object = responseMessage.getObject();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


        ProducerThread producerThread = new ProducerThread(serviceID,eventID,userID);
        ConsumerThread consumerThread = new ConsumerThread();

        producerThread.start();
        consumerThread.start();
        try {
            producerThread.join();
            consumerThread.join();
        }
        catch (InterruptedException e) {

        }
        return consumerThread.getObject();

    }
}