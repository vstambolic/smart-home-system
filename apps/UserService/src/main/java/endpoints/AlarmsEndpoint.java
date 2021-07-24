package endpoints;

import endpoints.jms_app_ids.AppId;
import entities.Alarm;
import entities.Event;
import entities.User;
import utils.HttpHeadersReader;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static endpoints.jms_app_ids.AlarmService.*;

@Path("alarms")
@Stateless
public class AlarmsEndpoint {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;


    @Resource(lookup = "smartHomeConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "smartHomeTopic")
    private Topic topic;


    @GET
    @Path("initialize")
    public Response initializeAlarms(@Context HttpHeaders httpHeaders) {
        User user = HttpHeadersReader.getUser(em,httpHeaders);
        callAlarmService(INITIALIZE,0,user.getIdUser());

        return Response.ok().build();
    }

    @GET
    @Path("terminate")
    public Response terminateAlarms() {
        callAlarmService(TERMINATE,0,0);
        return Response.ok().build();
    }


    @POST
    @Path("post_alarm")
    public Response postAlarm(@Context HttpHeaders httpHeaders, @FormParam("label") String label,
                              @FormParam("time") String time, @FormParam("date") String date, @FormParam("repetitive") short repetitive,
                              @FormParam("event") Integer event, @FormParam("ringtone") String ringtone) {

        User user = HttpHeadersReader.getUser(em,httpHeaders);
        em.refresh(user);
        Alarm alarm = new Alarm();
        alarm.setIdAlarm( em.createQuery("SELECT COALESCE(MAX(a.idAlarm), 0) FROM Alarm a", Long.class).getResultList().get(0).intValue()+1);
        alarm.setLabel(label);
        alarm.setRepetitive(repetitive);
        alarm.setIdTrack(user.getTrackList().stream()
                .filter(track -> track.getSpotifyURI().equals(ringtone))
                .findFirst()
                .get());
        alarm.setIdUser(user);
        alarm.setActive((short) 1);
        try {
            alarm.setTime( new SimpleDateFormat("HH:mm").parse(time));
            if (date!=null)
                alarm.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
        }
        if (event!=null)
            alarm.setEvent(em.find(Event.class,event));

        em.persist(alarm);
        em.flush();
        em.clear();

        this.callAlarmService(SET_NEW_ALARM,alarm.getIdAlarm(),user.getIdUser());

        return Response.ok(alarm).build();

    }

    private void callAlarmService(int serviceID, int alarmID, int userID) {
        JMSContext context = connectionFactory.createContext();       // Create message
        JMSProducer producer = context.createProducer();

        Message message=null;

        switch(serviceID) {
            case INITIALIZE: message = context.createObjectMessage(userID); break;
            case SWITCH:
            case SET_NEW_ALARM: message = context.createObjectMessage(alarmID); break;
            case TERMINATE: message = context.createMessage(); break;
        }


        int appID = AppId.ALARM.ordinal();                      // Set message properties

        try {
            message.setIntProperty("appID", appID);
            message.setIntProperty("serviceID", serviceID);

        } catch (JMSException e) {
            e.printStackTrace();
        }

        producer.send(topic, message);
    }


    @GET
    @Path("get_alarms")
    public Response getAlarms(@Context HttpHeaders httpHeaders) throws InterruptedException {
        User user = HttpHeadersReader.getUser(em, httpHeaders);     // Get user
        em.refresh(user);

        return Response.status(Response.Status.OK).entity(new GenericEntity<List<Alarm>>(user.getAlarmList()) {
        }).build();

    }

    @POST
    @Path("switch/{idAlarm}")
    public Response switchAlarm(@PathParam("idAlarm") int idAlarm) {
        Alarm alarm = em.find(Alarm.class, idAlarm);
        alarm.setActive((short) (alarm.getActive() == 1 ? 0 : 1));
        em.persist(alarm);
        em.flush();
        em.clear();
        this.callAlarmService(SWITCH,idAlarm,0);
        return Response.ok().build();
    }


    public void setEM(EntityManager em) {
        this.em = em;
    }
    public void setConnectionFactory(ConnectionFactory connectionFactory) { this.connectionFactory = connectionFactory;}
    public void setTopic(Topic topic) {this.topic = topic;}
}
