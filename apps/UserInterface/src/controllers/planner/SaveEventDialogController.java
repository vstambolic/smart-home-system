package controllers.planner;

import controllers.utils.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SaveEventDialogController extends Controller {

    @FXML
    public void  initialize() {
        System.out.println("s");
    }

    @FXML
    private Label notificationLabel;

    @FXML
    private Label travelDistanceLabel;

    @FXML
    private Label travelDurationLabel;


    public void setTravelDistance(double travelDistance) {
        this.travelDistanceLabel.setText(String.format("%.2f km", travelDistance));
    }

    public void setTravelDuration(double travelDuration) {
        this.travelDurationLabel.setText(Math.round(travelDuration) + " mins");
    }

    public void setMinutesLeft(long minutesLeft) {
        this.notificationLabel.setText("Your event starts in " + minutesLeft + " minutes. You won't be able to make it in time. Create event anyway?");
        this.notificationLabel.setVisible(true);
    }
}
