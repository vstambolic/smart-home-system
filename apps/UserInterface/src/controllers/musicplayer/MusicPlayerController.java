package controllers.musicplayer;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.httpConnection.HttpPostDataWriter;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class MusicPlayerController extends Controller {

    private static final String REFRESH_TOKEN = ""; // TODO: Spotify Refresh Token here
    private static final String CLIENT_ID ="";      // TODO: Spotify Client ID here
    private static final String CLIENT_SECRET ="";  // TODO: Spotify Client Secret here


    private static String ACCESS_TOKEN_FILE_PATH = "src\\resources\\musicplayer\\access_token.txt";
    private static String DEFAULT_CONTENT = "<body style=\"background-color:#181E2E\"></body>";

    @FXML
    public void initialize() {
        this.webView.getEngine().loadContent(DEFAULT_CONTENT);
    }

    @FXML
    private VBox musicPlayerBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private WebView webView;

    @FXML
    private Label trackLabel;

    @FXML
    private Label artistLabelHeader;

    @FXML
    private Label artistLabel;

    @FXML
    private Label albumLabelHeader;

    @FXML
    private Label albumLabel;

    @FXML
    private Label yearLabelHeader;

    @FXML
    private Label yearLabel;

    private String spotifyURI;

    @FXML
    public void myMusicAction(ActionEvent event) {
        this.webView.getEngine().loadContent(DEFAULT_CONTENT);

        BorderPane home = (BorderPane) musicPlayerBox.getParent();


        Parent myMusic = null;
        try {
            myMusic = FXMLLoader.load(getClass().getResource("..\\..\\resources\\MyMusic.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myMusic.translateXProperty().set(home.getWidth());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(myMusic.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        home.setCenter(myMusic);
    }

    @FXML
    public void returnAction(ActionEvent event) {
        this.webView.getEngine().loadContent(DEFAULT_CONTENT);

        BorderPane home = (BorderPane) musicPlayerBox.getParent();

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

    @FXML
    public  void saveAction(ActionEvent event) {
        if (this.spotifyURI != null)
            this.saveTrack();

    }

    private void saveTrack() {
        try {

            Map<String, Object> params = new LinkedHashMap<>();
            params.put("spotifyURI", this.spotifyURI);
            params.put("name", this.trackLabel.getText());
            params.put("album", this.albumLabel.getText());
            params.put("artist", this.artistLabel.getText());
            params.put("year", Integer.parseInt(this.yearLabel.getText()));


            HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/tracks/post").openConnection());
            HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());
            HttpPostDataWriter.writeData(params, connection);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("SVE OK");
            }






            } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private URL generateURL(String q) throws MalformedURLException {
        return new URL("https://api.spotify.com/v1/search?type=track&market=US&limit=1&q="+q.replace(" ","%20"));
    }

    private String getToken(String tokenPath) {
        File file = new File(tokenPath);

        String token=null;

        try {
            Scanner reader = new Scanner(file);
            System.out.println(token=reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return token;
    }

    private void refreshToken() {
        try {
            Process process = Runtime.getRuntime().exec("curl -H \"Authorization: Basic "+ Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes())+"\" -d grant_type=refresh_token -d refresh_token="+REFRESH_TOKEN+" https://accounts.spotify.com/api/token");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line="";
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            bufferedReader.close();
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            System.out.println(stringBuilder.toString());
            String freshAccessToken = jsonObject.getString("access_token");

            BufferedWriter writer = new BufferedWriter(new FileWriter(ACCESS_TOKEN_FILE_PATH));
            writer.write(freshAccessToken);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private HttpURLConnection connect(String q) throws IOException {
        HttpURLConnection connection =(HttpURLConnection) generateURL(q).openConnection();
        connection.setRequestMethod("GET");
        String authHeader = "Bearer " + this.getToken(ACCESS_TOKEN_FILE_PATH);
        connection.setRequestProperty("Authorization", authHeader);
        return connection;
    }

    private JSONObject getSpotifyTrack(String q) {
        try {
           HttpURLConnection connection = this.connect(q);

            if (connection.getResponseCode()==401) {
                this.refreshToken();
                connection = this.connect(q);
            }


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line="";
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            bufferedReader.close();
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            System.out.println(stringBuilder.toString());

            return jsonObject.getJSONObject("tracks").getJSONArray("items").getJSONObject(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @FXML
    public  void searchAction(ActionEvent event) throws InterruptedException {
        String searchText =  this.searchTextField.getText();
        Thread.sleep(6_000);
        if(!searchText.equals("")) {

            JSONObject jsonObject = this.getSpotifyTrack(searchText);

            String album = jsonObject.getJSONObject("album").getString("name");
            String releaseDate =jsonObject.getJSONObject("album").getString("release_date");

            JSONArray jsonArtists = jsonObject.getJSONArray("artists");
            String artists = jsonArtists.getJSONObject(0).getString("name");
            if (jsonArtists.length() > 1)
                for (int i=1;i<jsonArtists.length(); ++i)
                    artists+=", " + jsonArtists.getJSONObject(i).getString("name");

            String track = jsonObject.getString("name");
            String trackId = jsonObject.getString("id");

            //System.out.println("album: " + album + " releaseDate" + releaseDate + "artists" + artists + "name" + track + "id" + trackId);
            this.updateMusicInfo(album, releaseDate, artists, track, trackId);
            WebEngine webEngine = webView.getEngine();

            webEngine.loadContent("<body style=\"background-color:#181E2E;overflow:hidden;\">\n" +
                    "<iframe src=\"https://open.spotify.com/embed/track/" + trackId + "\" width=\"270\" height=\"330\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "</body>");

        }
    }

    private void updateMusicInfo(String album, String releaseDate, String artists, String track, String spotifyURI) {
        this.albumLabel.setText(album);
        this.albumLabel.setVisible(true);
        this.trackLabel.setText(track);
        this.trackLabel.setVisible(true);
        this.artistLabel.setText(artists);
        this.artistLabel.setVisible(true);
        this.yearLabel.setText(releaseDate.split("-")[0]);
        this.yearLabel.setVisible(true);

        this.albumLabelHeader.setVisible(true);
        this.artistLabelHeader.setVisible(true);
        this.yearLabelHeader.setVisible(true);

        this.spotifyURI = spotifyURI;
    }

}
