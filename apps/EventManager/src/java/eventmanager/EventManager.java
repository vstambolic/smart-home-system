package eventmanager;

import eventmanager.services.FetchCurrentEventService;
import eventmanager.services.FetchEventsService;
import eventmanager.services.Service;

import javax.annotation.Resource;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import static eventmanager.services.ServiceIDs.*;

/**
 *
 * @author vstambolic
 */
public class EventManager {

    public static int ID_EVENT_MANAGER = 2;
    public static final EntityManagerFactory EMF =  Persistence.createEntityManagerFactory("EventManagerPU");
    public static final EntityManager EM = EMF.createEntityManager();

    @Resource(lookup = "smartHomeConnectionFactory")
    private static ConnectionFactory CONNECTION_FACTORY;

    @Resource(lookup = "smartHomeTopic")
    private static Topic TOPIC;

    @Resource(lookup = "userServiceTopic")
    private static Topic USER_SERVICE_TOPIC;

    public static void main(String[] args) {


        JMSContext context = CONNECTION_FACTORY.createContext();
        JMSConsumer consumer = context.createConsumer(TOPIC,"appID="+ID_EVENT_MANAGER);
        JMSProducer producer = context.createProducer();

        Service service = null;
        System.out.println("Event manager has started.");

        while (true) {
            try {
                System.out.println("Waiting for new message...");
                Message message = consumer.receive();

                String timestamp = message.getStringProperty("timestamp");
                System.out.println("received timestamp: " + timestamp);


                switch (message.getIntProperty("serviceID")) {                                       // Delegate service
                    case FETCH_CURRENT: {
                        System.out.println("Message: FETCH CURRENT");
                       // service = new FetchCurrentEventService((Integer) ((ObjectMessage) message).getObject());
                        break;
                    }
                    case FETCH_ALL: {
                        System.out.println("Message: FETCH ALL");
                        service = new FetchEventsService((Integer) ((ObjectMessage) message).getObject());

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
