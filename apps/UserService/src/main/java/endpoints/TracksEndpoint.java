package endpoints;


import endpoints.jms_app_ids.AppId;
import endpoints.jms_app_ids.MusicPlayerService;
import entities.Track;
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
import java.util.ArrayList;
import java.util.List;

@Path("tracks")
@Stateless
public class TracksEndpoint {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Resource(lookup = "smartHomeConnectionFactory")
    private ConnectionFactory CONNECTION_FACTORY;

    @Resource(lookup = "smartHomeTopic")
    private Topic TOPIC;

    @Resource(lookup = "userServiceTopic")
    private Topic USER_SERVICE_TOPIC;

     @GET
     @Path("collection")
     public Response getTracks(@Context HttpHeaders httpHeaders) throws InterruptedException {

         User user = HttpHeadersReader.getUser(em, httpHeaders);        // Get user
         em.refresh(user);

//         return Response.status(Response.Status.OK).entity(new GenericEntity<List<Track>>(user.getTrackList()){}).build();


         String timestamp = String.valueOf(System.currentTimeMillis());


         class ProducerThread extends Thread {
             @Override
             public void run() {
                 JMSContext context = CONNECTION_FACTORY.createContext();       // Create message
                 JMSProducer producer = context.createProducer();

                 Message message = context.createObjectMessage(user.getIdUser());

                 int appID = AppId.MUSIC_PLAYER.ordinal();                      // Set message properties
                 int serviceID = MusicPlayerService.GET_TRACKS;

                 try {
                     message.setIntProperty("appID", appID);
                     message.setIntProperty("serviceID", serviceID);
                     message.setStringProperty("timestamp", timestamp);

                 } catch (JMSException e) {
                     e.printStackTrace();
                 }

                 producer.send(TOPIC, message);                                 // Send message
             }
         }

//         Thread producerThread = new Thread() {
//             @Override
//             public void run() {
//                 JMSContext context = CONNECTION_FACTORY.createContext();       // Create message
//                 JMSProducer producer = context.createProducer();
//
//                 Message message = context.createObjectMessage(user.getIdUser());
//
//                 int appID = AppId.MUSIC_PLAYER.ordinal();                      // Set message properties
//                 int serviceID = MusicPlayerService.GET_TRACKS;
//
//                 try {
//                     message.setIntProperty("appID", appID);
//                     message.setIntProperty("serviceID", serviceID);
//                     message.setStringProperty("timestamp", timestamp);
//
//                 } catch (JMSException e) {
//                     e.printStackTrace();
//                 }
//
//                 producer.send(TOPIC, message);                                 // Send message
//             }
//         };

         class ConsumerThread extends Thread {
             private List<Track> tracks;
             public List<Track> getTracks() {
                 return tracks;
             }

             @Override
             public void run() {
                 JMSContext context = CONNECTION_FACTORY.createContext();
                 JMSConsumer consumer = context.createConsumer(USER_SERVICE_TOPIC, "timestamp=" +"'"+timestamp+"'");     // Wait for jms response
                 ObjectMessage responseMessage = (ObjectMessage) consumer.receive();
                 try {
                     this.tracks = (List<Track>)responseMessage.getObject();
                 } catch (JMSException e) {
                     e.printStackTrace();
                 }
             }
         }


//             Thread consumerThread = new Thread() {
//
//
//             private List<Track> alarms;
//             public List<Track> getTracks() {
//                 return alarms;
//             }
//
//             @Override
//             public void run() {
//                 JMSContext context = CONNECTION_FACTORY.createContext();
//                 JMSConsumer consumer = context.createConsumer(TOPIC, "timestamp=" +"'"+timestamp+"'");     // Wait for jms response
//                 ObjectMessage responseMessage = (ObjectMessage) consumer.receive();
//                 try {
//                     this.alarms = (List<Track>)responseMessage.getObject();
//                 } catch (JMSException e) {
//                     e.printStackTrace();
//                 }
//             }
//         };

         ProducerThread producerThread = new ProducerThread();
         ConsumerThread consumerThread = new ConsumerThread();

         producerThread.start();
         consumerThread.start();
         producerThread.join();
         consumerThread.join();

//         return Response.ok().entity(timestamp).build();
         return Response.status(Response.Status.OK).entity(new GenericEntity<List<Track>>(consumerThread.getTracks()){}).build();
     }

     @POST
     @Path("post")
     public Response postTrack(@Context HttpHeaders httpHeaders, @FormParam("spotifyURI") String spotifyURI,
                              @FormParam("name") String name, @FormParam("album") String album,
                              @FormParam("artist") String artist, @FormParam("year") int year) {


         User user = HttpHeadersReader.getUser(em,httpHeaders);

         Track track = em.find(Track.class, spotifyURI);

         if (track==null)
             track = new Track(spotifyURI, name, artist, album, year);

         if (track.getUserList() == null) {
             List<User> list = new ArrayList<>(1);
             list.add(user);
             track.setUserList(list);
         }
         else
             if (!track.getUserList().contains(user))
                track.getUserList().add(user);

         em.persist(track);
         em.flush();
         em.clear();

         return Response.ok().build();

     }


}
