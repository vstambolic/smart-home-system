package controllers.alarm;

import entities.Alarm;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;
import utils.ui.SwitchButton;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class AlarmRowController {

    @FXML
    private Label timeLabel;

    @FXML
    private Label labelLabel;

    @FXML
    private Label repetitiveLabel;


    @FXML
    private Label trackLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private BorderPane switchButtonWrapper;

    private SwitchButton switchButton;

    private Alarm alarm;



    @FXML
    public void initialize() {
        try {
            this.switchButtonWrapper.setCenter(this.switchButton = new SwitchButton(this.getClass().getMethod("switchActive"),this));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
    public void setLabelLabel(String label) {
        this.labelLabel.setText(label);
    }

    public void setRepetitiveLabel(String repetitive) {
        this.repetitiveLabel.setText(repetitive);
    }

    public void setTrackLabel(String track) {
        this.trackLabel.setText(track);
    }

    public void setArtistLabel(String artist) {
        this.artistLabel.setText(artist);
    }


    public void setTimeLabel(String time) {
        this.timeLabel.setText(time);
    }

    public void setActive(short active) {
        this.switchButton.setSwitchedOn(active==1 ? true: false);
    }

    public void switchActive() {
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/alarms/switch/"+this.alarm.getIdAlarm()).openConnection());
            HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
            connection.setRequestMethod("POST");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                System.out.println("Switchovao");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(this.switchButton.getSwitchedOn().get());
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;

        this.setTimeLabel(new SimpleDateFormat("HH:mm").format(alarm.getTime()));
        this.setLabelLabel(alarm.getLabel());
        this.setTrackLabel(alarm.getIdTrack().getName());
        this.setArtistLabel(alarm.getIdTrack().getArtist());
        this.setRepetitiveLabel(alarm.getRepetitive()==1? "Every Day" : "Only Ring Once");
        this.setActive(alarm.getActive());
    }
}
