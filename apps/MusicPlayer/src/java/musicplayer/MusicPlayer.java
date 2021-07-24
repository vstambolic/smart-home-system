package musicplayer;

import services.GetTracksService;
import services.PostTrackService;
import services.Service;

import javax.annotation.Resource;
import javax.jms.*;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static services.ServiceIDs.GET_TRACKS;
import static services.ServiceIDs.POST_TRACK;

/**
 *
 * @author vstambolic
 */

public class MusicPlayer {

    public static int ID_MUSIC_PLAYER = 1;
    public static final EntityManagerFactory EMF =  Persistence.createEntityManagerFactory("MusicPlayerPU");
    public static final EntityManager EM = EMF.createEntityManager();
   
    @Resource(lookup = "smartHomeConnectionFactory")
    private static ConnectionFactory CONNECTION_FACTORY;

    @Resource(lookup = "smartHomeTopic")
    private static Topic TOPIC;
//
    @Resource(lookup = "userServiceTopic")
    private static Topic USER_SERVICE_TOPIC;
    

    public static void main(String[] args)  {

        JMSContext context = CONNECTION_FACTORY.createContext();
        JMSConsumer consumer = context.createConsumer(TOPIC,"appID="+ID_MUSIC_PLAYER);
        JMSProducer producer = context.createProducer();

        Service service = null;
        System.out.println("Music player has started.");



        while (true) {
            try {
                System.out.println("Waiting for new message...");
                Message message = consumer.receive();

                String timestamp = message.getStringProperty("timestamp");
                System.out.println("received: " + timestamp);


                switch (message.getIntProperty("serviceID")) {                                       // Delegate service
                    case GET_TRACKS: {
                        System.out.println("Message: get alarms");
                        service = new GetTracksService((Integer) ((ObjectMessage) message).getObject());
                        break;
                    }
                    case POST_TRACK: {
                        System.out.println("Message: post track");
                        service = new PostTrackService();
                        break;
                    }
                }

                Object object = service.execute();
                ObjectMessage responseMessage = context.createObjectMessage();
                responseMessage.setObject((Serializable) object);

                responseMessage.setStringProperty("timestamp", timestamp);

                producer.send(USER_SERVICE_TOPIC, responseMessage);
                System.out.println("Response message sent.");


            } catch (JMSException e) {
                e.printStackTrace();
            }

        }







    }
    
}
