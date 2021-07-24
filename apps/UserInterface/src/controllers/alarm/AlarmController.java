package controllers.alarm;

import controllers.utils.Controller;
import entities.User;
import entities.lists.Alarms;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;
import utils.httpConnection.HttpRequestBodyReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlarmController extends Controller {

    @FXML
    private VBox alarmBox;
    @FXML
    private VBox alarmTable;

    public static Alarms fetchAlarms() throws IOException, JAXBException {
        HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/alarms/get_alarms").openConnection());
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);

        HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("Fetching alarms...");
            JAXBContext jaxbContext = JAXBContext.newInstance(Alarms.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Alarms) unmarshaller.unmarshal(new StreamSource(new StringReader(HttpRequestBodyReader.readInputStream(connection))));
        }
        return null;
    }

    @FXML
    public void initialize() {
        try {
            Alarms alarms = fetchAlarms();
            if (alarms.getAlarms() != null) {
                System.out.println(alarms.getAlarms());
                alarms.getAlarms().forEach(alarm -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\AlarmRow.fxml"));
                        HBox row = loader.load();
                        AlarmRowController controller = loader.getController();
                        controller.setAlarm(alarm);
                        this.alarmTable.getChildren().add(row);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (JAXBException jaxbException) {
            jaxbException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void returnAction(ActionEvent event) {
        BorderPane home = (BorderPane) alarmBox.getParent();

        Parent homeMenu = null;
        try {
            homeMenu = FXMLLoader.load(getClass().getResource("..\\..\\resources\\HomeMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeMenu.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(homeMenu.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(homeMenu);

    }

    @FXML
    public void newAlarmAction(ActionEvent event) {
        BorderPane home = (BorderPane) alarmBox.getParent();

        Parent newAlarm = null;
        try {
            newAlarm = FXMLLoader.load(getClass().getResource("..\\..\\resources\\NewAlarm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newAlarm.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(newAlarm.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(newAlarm);
       // this.addAlarm();
    }

}
