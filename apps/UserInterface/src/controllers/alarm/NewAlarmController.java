package controllers.alarm;

import controllers.musicplayer.MyMusicController;
import controllers.utils.Controller;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import utils.httpConnection.HttpPostDataWriter;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;

import javax.xml.bind.JAXBException;


public class NewAlarmController extends Controller {

    @FXML
    private VBox newAlarmBox;
    @FXML
    private TextField alarmLabelTextField;
    @FXML
    private Label hoursLabel;
    private int hours=8;
    @FXML
    private Label minutesLabel;
    private int minutes=0;


    @FXML
    private ComboBox<String> ringtonesComboBox;

    @FXML
    private ToggleGroup repeatRadioButton;
    private List<Track> tracks;


    @FXML
    public void cancelAction(ActionEvent event) {
        this.showAlarmHomePage();
    }


    @FXML
    public void createNewAlarmAction(ActionEvent event) {
        String label =  this.alarmLabelTextField.getText().equals("")? "New Alarm" : this.alarmLabelTextField.getText();
        String time = String.format("%02d:%02d", this.hours, this.minutes);
        boolean repetitive = ((RadioButton)repeatRadioButton.getSelectedToggle()).getText().equals("Every day");
        String ringtone = this.tracks.get(this.ringtonesComboBox.getSelectionModel().getSelectedIndex()).getSpotifyURI();
        postAlarm(label,time,null,repetitive,ringtone);

        this.showAlarmHomePage();
    }

    public static void postAlarm(String label,String time, String date, boolean repetitive, String ringtone) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put("label",label);
            params.put("time", time);
            if (date!=null)
                params.put("date", date);
            params.put("repetitive", repetitive? 1:0);
            params.put("ringtone", ringtone);

            HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/alarms/post_alarm").openConnection());
            HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
            HttpPostDataWriter.writeData(params, connection);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Success.");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlarmHomePage() {
        System.out.println(repeatRadioButton.getSelectedToggle());
        BorderPane home = (BorderPane) newAlarmBox.getParent();

        Parent alarmHome = null;
        try {
            alarmHome = FXMLLoader.load(getClass().getResource("..\\..\\resources\\Alarm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        alarmHome.translateXProperty().set(home.getWidth());

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(alarmHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(alarmHome);
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
//        ringtonesComboBox.setValue("Option");

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

}
