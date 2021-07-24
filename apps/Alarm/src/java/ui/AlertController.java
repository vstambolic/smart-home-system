package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AlertController {

    @FXML
    private Label timeLabel;

    @FXML
    private Label alarmLabel;

    @FXML
    private WebView webView;

    private static String DEFAULT_CONTENT = "<body style=\"background-color:#181E2E\"></body>";

    @FXML
    public void initialize() {
        this.webView.getEngine().loadContent(DEFAULT_CONTENT);
    }

    public void setTime(String time) {
        this.timeLabel.setText(time);
    }
    public void setLabel(String label) {
        this.alarmLabel.setText(label);
    }
    public void setRingtone(String spotifyURI) {
        try {
            Desktop.getDesktop().browse(new URI("<body style=\"background-color:#181E2E;overflow:hidden;\">\n" +
                    "<iframe src=\"https://open.spotify.com/embed/track/" + spotifyURI + "\" width=\"270\" height=\"330\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "</body>"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


//        this.webView.getEngine().loadContent("<body style=\"background-color:#181E2E;overflow:hidden;\">\n" +
//                "<iframe src=\"https://open.spotify.com/embed/track/" + spotifyURI + "\" width=\"270\" height=\"330\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
//                "</body>");
    }

}
