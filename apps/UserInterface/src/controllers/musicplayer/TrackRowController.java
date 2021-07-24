package controllers.musicplayer;

import controllers.utils.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TrackRowController extends Controller {
    @FXML
    private Label trackLabel;

    @FXML
    private Label infoLabel;


    public void setTrack(String track) {
        this.trackLabel.setText(track);
    }

    public void setInfo(String info) {
        this.infoLabel.setText(info);
    }
}
