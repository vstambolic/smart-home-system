package controllers;

import controllers.utils.Controller;
import entities.User;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;
import utils.ui.Images;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class HomeController extends Controller {

    @FXML
    private Label userLabel;

    @FXML
    private Pane homeMenu;
    @FXML
    private BorderPane home;

    @FXML
    private Circle circle;



    @FXML
    public void initialize() {
        if (this.userLabel!=null)
          this.userLabel.setText(User.getCurrentUser().getFirstName());
        if (this.circle!=null) {
            circle.setFill(new ImagePattern(Images.personImage));
        }
    }

    @FXML
    public void alarmAction(ActionEvent event) {
        Parent alarmHome = null;
        try {
            alarmHome = FXMLLoader.load(getClass().getResource("..\\resources\\Alarm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BorderPane home = (BorderPane)(homeMenu.getParent());
        alarmHome.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(alarmHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(alarmHome);
    }

    @FXML
    public void detailsButtonAction(ActionEvent event) {
        Parent details = null;
        try {
            details = FXMLLoader.load(getClass().getResource("..\\resources\\Details.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        details.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(details.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(details);
    }

    @FXML
    public void musicPlayerAction(ActionEvent event) {
        Parent musicPlayerHome = null;
        try {

            musicPlayerHome = FXMLLoader.load(getClass().getResource("../resources/MusicPlayer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BorderPane home = (BorderPane)(homeMenu.getParent());
        musicPlayerHome.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(musicPlayerHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(musicPlayerHome);
    }

    @FXML
    public void plannerAction(ActionEvent event) {
        Parent plannerHome = null;
        try {
            plannerHome = FXMLLoader.load(getClass().getResource("..\\resources\\Planner.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BorderPane home = (BorderPane)(homeMenu.getParent());
        plannerHome.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(plannerHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(plannerHome);

    }

    @FXML
    public void signOutAction(ActionEvent event) {

        try {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            DialogPane dp = FXMLLoader.load(getClass().getResource("..\\resources\\SignOutDialog.fxml"));
            alert.setTitle("Sign out");
            alert.setDialogPane(dp);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                this.terminateAlarms();
               this.returnToLogIn();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void terminateAlarms() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/UserService/s/alarms/terminate").openConnection();
            connection.setRequestMethod("GET");
            HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Successfully initialized alarms.");
            }
        }
        catch (IOException e) {

        }
    }


    private void returnToLogIn() {
        Parent login = null;
        try {
            login = FXMLLoader.load(getClass().getResource("..\\resources\\LogIn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage)(home.getScene().getWindow());
        stage.setScene(new Scene(login));

    }

}
