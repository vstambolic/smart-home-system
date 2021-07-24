package controllers.planner;

import entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import utils.ui.Images;

import java.text.SimpleDateFormat;

public class EventRowController {

    @FXML
    private Label labelLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;


    private Event event;



    @FXML
    public void initialize() {

        editButton.setGraphic(new ImageView(Images.editImage));
        deleteButton.setGraphic(new ImageView(Images.deleteImage));
    }


    @FXML
    void deleteAction(ActionEvent event) {
        /*TODO*/
    }

    @FXML
    void editAction(ActionEvent event) {
        /*TODO*/

    }

    public void setEvent(Event event) {
        this.event = event;

        this.labelLabel.setText(event.getLabel());
        this.addressLabel.setText(event.getAddress());
        this.dateLabel.setText(new SimpleDateFormat("dd/MM/yy").format(event.getDate()));
        this.timeLabel.setText(new SimpleDateFormat("HH:mm").format(event.getTime()));

    }
}
