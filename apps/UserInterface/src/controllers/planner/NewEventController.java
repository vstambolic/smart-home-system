package controllers.planner;

import controllers.alarm.AlarmController;
import controllers.alarm.NewAlarmController;
import controllers.musicplayer.MyMusicController;
import controllers.utils.Controller;
import controllers.utils.LocationParentController;
import entities.Track;
import entities.User;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.JSONObject;
import utils.httpConnection.HttpPostDataWriter;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewEventController extends LocationParentController {
    private static String API_KEY = ""; // TODO Bing Maps API Key HERE

    @FXML
    private StackPane stackPane;

    private PlannerController plannerController;
    private List<Track> tracks;

    public void setPlannerController(PlannerController plannerController) {
        this.plannerController = plannerController;

        this.latitude = plannerController.getLatitude();
        this.longitude = plannerController.getLongitude();

        this.locationButton.setText( this.address = plannerController.getAddress());
    }

    @FXML
    public void initialize() {
            this.hoursLabel.setOnScroll(event -> {
                if (event.getDeltaY() > 0)
                    this.incHours(null);
                else
                    this.decHours(null);
            });

            this.minutesLabel.setOnScroll(event -> {
                if (event.getDeltaY() > 0)
                    this.incMinutes(null);
                else
                    this.decMinutes(null);
            });

        try {
            this.tracks = MyMusicController.getTracks().getTracks();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        if (this.tracks!=null)
            this.tracks.forEach(track -> {
                ringtonesComboBox.getItems().add(track.getName() + " - " + track.getArtist());
            });

        datePicker.setValue(LocalDate.now());

    }


    @FXML
    private VBox newEventBox;

    @FXML
    private TextField eventLabelTextField;

    @FXML
    private Label hoursLabel;
    private int hours=8;
    @FXML
    private Label minutesLabel;
    private int minutes=0;

    @FXML
    private ComboBox<String> ringtonesComboBox;
    @FXML
    private DatePicker datePicker;


    @FXML
    private Button locationButton;
    private double latitude;
    private double longitude;
    private String address;



    @FXML
    public void cancelAction(ActionEvent event) {
        this.showPlannerHomePage();
    }

    private URL generateURL() throws MalformedURLException {
        String origins = this.plannerController.getLatitude() + "," + this.plannerController.getLongitude();
        String destinations = this.getLatitude() + "," + this.getLongitude();
        String urlStr = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?origins=" + origins + "&destinations=" + destinations + "&travelMode=driving&key=" + API_KEY;
        return new URL(urlStr);

    }

    private JSONObject getTravelInfo() {
        try {
             HttpURLConnection httpURLConnection=(HttpURLConnection)  generateURL().openConnection();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
             JSONObject jsonObject = new JSONObject(bufferedReader.readLine());
            return jsonObject.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0).getJSONArray("results").getJSONObject(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void createNewEventAction(ActionEvent event) {
        JSONObject jsonResult = this.getTravelInfo();
        double travelDistance = jsonResult.getDouble("travelDistance");
        double travelDuration = jsonResult.getDouble("travelDuration");
        System.out.println("distance " + travelDistance + " duration " + travelDuration);

        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\SaveEventDialog.fxml"));
            DialogPane dp = loader.load();

            SaveEventDialogController controller = loader.getController();
            controller.setTravelDistance(travelDistance);
            controller.setTravelDuration(travelDuration);

            long eventTime = this.getEventTime();
            long minutesLeft = this.calculateTimeLeft(eventTime);
            if (minutesLeft > 0 && minutesLeft < travelDuration)
                controller.setMinutesLeft(minutesLeft);

            alert.setTitle("Save event");
            alert.setDialogPane(dp);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                this.postEvent();
                this.postReminder(eventTime, Math.round(travelDuration*60*1000));
                this.showPlannerHomePage();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postReminder(long eventTime, long travelDuration) {
        String label = this.eventLabelTextField.getText().equals("")? "Event - Reminder" : this.eventLabelTextField.getText() + " - Reminder";
        Date date = new Date(eventTime-travelDuration);
        String timeStr = new SimpleDateFormat("HH:mm").format(date);
        String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String ringtone = this.tracks.get(this.ringtonesComboBox.getSelectionModel().getSelectedIndex()).getSpotifyURI();
        NewAlarmController.postAlarm(label,timeStr,dateStr,false,ringtone);
    }

    private void postEvent() {
        try {


            Map<String, Object> params = new LinkedHashMap<>();

            params.put("label", this.eventLabelTextField.getText().equals("")? "Event" : this.eventLabelTextField.getText());
            params.put("time", String.format("%02d:%02d", this.hours, this.minutes));
            params.put("date",datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            params.put("address",address.equals("Home")? User.getCurrentUser().getHomeAddress():address);
            params.put("latitude",latitude);
            params.put("longitude",longitude);
            params.put("ringtone", this.tracks.get(this.ringtonesComboBox.getSelectionModel().getSelectedIndex()).getSpotifyURI());


            HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/events/post_event").openConnection());
            HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
            HttpPostDataWriter.writeData(params, connection);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Success: posted event.");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPlannerHomePage() {
        BorderPane home = (BorderPane) stackPane.getParent();

        Parent plannerHome = null;
        try {
            plannerHome = FXMLLoader.load(getClass().getResource("..\\..\\resources\\Planner.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        plannerHome.translateXProperty().set(home.getWidth());

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(plannerHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(plannerHome);
    }


    @FXML
    public void decHours(ActionEvent event) {
        if (this.hours!=0)
            this.hoursLabel.setText(String.format("%02d",--hours));
    }

    @FXML
    public void decMinutes(ActionEvent event) {
        if (this.minutes!=0)
            this.minutesLabel.setText(String.format("%02d",--minutes));

    }

    @FXML
    public void incHours(ActionEvent event) {
        if (this.hours!=23)
            this.hoursLabel.setText(String.format("%02d",++hours));
    }

    @FXML
    public void incMinutes(ActionEvent event) {
        if (this.minutes!=59)
            this.minutesLabel.setText(String.format("%02d",++minutes));
    }


    private long getEventTime() {
        LocalDateTime localDateTime = LocalDateTime.of(this.datePicker.getValue(),LocalTime.of(this.hours,this.minutes));
        return Timestamp.valueOf(localDateTime).getTime();
    }

    private long calculateTimeLeft(long eventTime) {
        System.out.println(TimeUnit.MILLISECONDS.toMinutes(eventTime-System.currentTimeMillis()));
        return TimeUnit.MILLISECONDS.toMinutes(eventTime-System.currentTimeMillis());
    }
    // Location ----------------------------------

    @FXML
    public void locationAction(ActionEvent event) {
        try {
            stackPane.getChildren().remove(1);
        }
        catch (IndexOutOfBoundsException e) {

        }

        VBox locationHome = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\Location.fxml"));
            locationHome = loader.load();
            LocationController lc = loader.getController();
            lc.setParentController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }


        locationHome.translateXProperty().set(stackPane.getWidth());

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(locationHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        this.stackPane.getChildren().add(locationHome);

    }


    public double getLatitude() {
        return latitude;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setAddress(String address) {
        this.locationButton.setText(this.address=address);
    }

    public void setLatitude(double lat) {
        this.latitude=lat;
    }

    public void setLongitude(double lon) {
        this.longitude=lon;
    }


}
