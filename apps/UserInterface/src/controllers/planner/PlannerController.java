package controllers.planner;

import controllers.alarm.AlarmRowController;
import controllers.utils.Controller;
import entities.Event;
import entities.User;
import entities.lists.Alarms;
import entities.lists.Events;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class PlannerController extends Controller {

    @FXML
    private VBox plannerBox;

    @FXML
    private VBox eventTable;

    @FXML
    private Label currEventLabel;

    @FXML
    private Label currEventAddress;


    private double latitude;

    private double longitude;

    private String address="Home";

    @FXML
    public void initialize() {
        try {
            Event currentEvent = this.fetchCurrentEvent();

            if (currentEvent == null || currentEvent.getAddress().equals(User.getCurrentUser().getHomeAddress())) {
                this.currEventLabel.setText(currentEvent == null ? "None" : currentEvent.getLabel());
                this.currEventAddress.setText(this.address = "Home");
                this.longitude = User.getCurrentUser().getHomeLongitude();
                this.latitude = User.getCurrentUser().getHomeLatitude();
            }
            else {
                this.currEventLabel.setText(currentEvent.getLabel());
                this.currEventAddress.setText(this.address= currentEvent.getAddress());
                this.longitude = currentEvent.getLongitude();
                this.latitude = currentEvent.getLatitude();
            }

            List<Event> events = fetchEvents().getEvents();
            if (events!=null) {
                System.out.println(events);
                events.forEach(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\EventRow.fxml"));
                        HBox row = loader.load();
                        EventRowController controller = loader.getController();
                        controller.setEvent(event);
                        this.eventTable.getChildren().add(row);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    private static Events fetchEvents() throws IOException, JAXBException {
        HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/events/get_events").openConnection());
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("Fetching events...");
            JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (Events) unmarshaller.unmarshal(new StreamSource(new StringReader(HttpRequestBodyReader.readInputStream(connection))));
        }
        return null;

    }

    private Event fetchCurrentEvent() throws IOException, JAXBException {
        HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/events/get_current_event").openConnection());
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());


        switch (connection.getResponseCode()) {
            default:
            case HttpURLConnection.HTTP_NO_CONTENT:
                return null;
            case HttpURLConnection.HTTP_OK:
                System.out.println("Fetching current event...");
                JAXBContext jaxbContext = JAXBContext.newInstance(Event.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (Event) unmarshaller.unmarshal(new StreamSource(new StringReader(HttpRequestBodyReader.readInputStream(connection))));
        }

    }

    @FXML
    void newEventAction(ActionEvent event) {
        BorderPane home = (BorderPane) plannerBox.getParent();

        Parent newEvent = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\NewEvent.fxml"));
            newEvent = loader.load();
            NewEventController controller = loader.getController();
            controller.setPlannerController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        newEvent.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(newEvent.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(newEvent);
    }

    @FXML
    void returnAction(ActionEvent event) {
        BorderPane home = (BorderPane) plannerBox.getParent();

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




    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return this.address;
    }
}
