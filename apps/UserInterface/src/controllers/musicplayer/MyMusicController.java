package controllers.musicplayer;

import controllers.utils.Controller;
import entities.User;
import entities.lists.Tracks;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.net.URL;


public class MyMusicController extends Controller {


    public static Tracks getTracks() throws IOException, JAXBException {
        HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/tracks/collection").openConnection());
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);

        HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("STIZE MUZIKA~!!");
            JAXBContext jaxbContext = JAXBContext.newInstance(Tracks.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Tracks) unmarshaller.unmarshal(new StreamSource(new StringReader(HttpRequestBodyReader.readInputStream(connection))));
        }
        return null;
    }

    @FXML
    private VBox myMusicBox;

    @FXML
    private VBox myMusicTable;

    @FXML
    public void initialize() {
        try {
            Tracks tracks = getTracks();
            if (tracks.getTracks() != null)
                tracks.getTracks().forEach(track -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\MusicTrackRow.fxml"));
                        HBox row = loader.load();
                        TrackRowController controller = loader.getController();
                        controller.setTrack(track.getName());
                        controller.setInfo(track.getArtist() + " (" + track.getAlbum() + ", " + track.getYear() + ")");

                        MyMusicController.this.myMusicTable.getChildren().add(row);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        } catch (JAXBException jaxbException) {
            jaxbException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    void returnAction(ActionEvent event) {
        BorderPane home = (BorderPane) myMusicBox.getParent();

        Parent musicPlayer = null;
        try {
            musicPlayer = FXMLLoader.load(getClass().getResource("..\\..\\resources\\MusicPlayer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        musicPlayer.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(musicPlayer.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(musicPlayer);
    }

}
