package controllers.users;

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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class DetailsController extends Controller {
    @FXML
    private VBox detailsBox;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    public void initialize() {
        User user = User.getCurrentUser();
        this.firstNameLabel.setText(user.getFirstName());
        this.lastNameLabel.setText(user.getLastName());
        this.emailLabel.setText(user.getEmail());
        this.usernameLabel.setText(user.getUsername());
        this.addressLabel.setText(user.getHomeAddress());
    }



    @FXML
    void returnAction(ActionEvent event) {
        BorderPane home = (BorderPane) detailsBox.getParent();

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

}
