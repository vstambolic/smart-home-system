package alarm;

import alarm.services.*;
import entities.Alarm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.AlertController;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;


import javax.jms.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static alarm.services.ServiceIDs.*;


/**
 *
 * @author vstambolic
 */
public class AlarmManager extends Application {

    public static int ID_ALARM = 0;
    public static final EntityManagerFactory EMF =  Persistence.createEntityManagerFactory("AlarmPU");
    public static final EntityManager EM = EMF.createEntityManager();

    @Resource(lookup = "smartHomeConnectionFactory")
    private static ConnectionFactory CONNECTION_FACTORY;

    @Resource(lookup = "smartHomeTopic")
    private static Topic TOPIC;

    public static void main(String[] args)  {
        launch(args);
    }


    public static void showAlert(Alarm alarm) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            FXMLLoader loader = new FXMLLoader(AlertController.class.getResource("AlarmAlert.fxml"));
            DialogPane dp = loader.load();

            AlertController controller = loader.getController();
            controller.setLabel(alarm.getLabel());
            controller.setTime(new SimpleDateFormat("HH:mm").format(alarm.getTime()));
            controller.setRingtone(alarm.getIdTrack().getSpotifyURI());
            alert.setDialogPane(dp);

            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Node closeButton = alert.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);

            Desktop.getDesktop().browse(new URI("https://open.spotify.com/embed/track/" +alarm.getIdTrack().getSpotifyURI() ));
            alert.show();
        }
        catch (IOException | URISyntaxException e) {
        }

    }
    private static Thread MANAGER = new Thread(){
        @Override
        public void run() {
            JMSContext context = CONNECTION_FACTORY.createContext();
            JMSConsumer consumer = context.createConsumer(TOPIC, "appID=" + ID_ALARM);

            Service service = null;
            System.out.println("Alarm manager has started.");

            while (true) {
                try {
                    System.out.println("Waiting for new message...");
                    Message message = consumer.receive();

                    switch (message.getIntProperty("serviceID")) { // Delegate service
                        case INITIALIZE: {
                            System.out.println("Message: INITIALIZE");
                            service = new InitializeService((Integer) ((ObjectMessage) message).getObject()); // User

                            break;
                        }
                        case SET_NEW_ALARM: {
                            System.out.println("Message: SET NEW ALARM");
                            service = new SetNewAlarmService((Integer) ((ObjectMessage) message).getObject());
                            break;
                        }
                        case TERMINATE: {
                            System.out.println("Message: TERMINATE");
                            service = new TerminateService();
                        }
                        case SWITCH: {

                        }
                    }
                    service.execute();


                } catch(JMSException e){
                    e.printStackTrace();

                }

            }
        }
    };
 
    @Override
    public void start(Stage primaryStage) throws Exception {
       MANAGER.start();
    }

}
