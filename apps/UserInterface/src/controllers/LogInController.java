package controllers;

import controllers.alarm.AlarmController;
import controllers.utils.Controller;
import entities.User;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import utils.httpConnection.HttpPostDataWriter;
import utils.httpConnection.HttpRequestAuthorizationHeaderWriter;
import utils.httpConnection.HttpRequestBodyReader;
import utils.ui.Images;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class LogInController extends Controller {


    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label label;

    @FXML
    private Circle circle;

    @FXML
    public void initialize() {

        circle.setFill(new ImagePattern(Images.personImage));


        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        if (passwordTextField!=null) {
            passwordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && firstTime.get()) {
                    passwordTextField.getParent().requestFocus(); // Delegate the focus to container
                    firstTime.setValue(false); // Variable value changed for future references
                }
            });
        }
        final BooleanProperty firstTime1 = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        if (usernameTextField!=null) {
            usernameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && firstTime1.get()) {
                    usernameTextField.getParent().requestFocus(); // Delegate the focus to container
                    firstTime1.setValue(false); // Variable value changed for future references
                }
            });
        }
    }

    @FXML
    public void checkCredentials(ActionEvent event) throws IOException, InterruptedException {
        label.setVisible(true);

        if (this.checkCredentials()) {
            label.setText("Welcome " + "user!");
            this.initializeAlarms();

            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(666);
                } catch (InterruptedException exc) {
                    throw new Error("Unexpected interruption", exc);
                }
                Platform.runLater(() -> {
                    Parent home = null;
                    try {
                        home = FXMLLoader.load(getClass().getResource("../resources/Home.fxml"));
                        System.out.println("jee");
                        ((BorderPane)home).setCenter(FXMLLoader.load(getClass().getResource("../resources/HomeMenu.fxml")));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene homeScene = new Scene(home);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(homeScene);});
            });
            thread.setDaemon(true);
            thread.start();
        }
        else {
            label.setText("Username or password is incorrect.");
        }


    }

    private void initializeAlarms() {
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/UserService/s/alarms/initialize").openConnection();
            connection.setRequestMethod("GET");
            HttpRequestAuthorizationHeaderWriter.setBasicAuthHeader(connection, User.getCurrentUser());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Successfully initialized alarms.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkCredentials() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/UserService/s/users/login").openConnection();
            conn.setRequestMethod("GET");

            String authHeader =  "Basic " +  Base64.getEncoder().encodeToString((this.usernameTextField.getText()+":"+this.passwordTextField.getText()).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", authHeader);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                User user = (User) unmarshaller.unmarshal( new StreamSource( new StringReader( HttpRequestBodyReader.readInputStream(conn) ) ) );
                System.out.println(User.getCurrentUser());
                return true;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return false;

    }


    @FXML
    void createAnAccountAction(MouseEvent event) {
        Parent registerPane = null;
        try {
            registerPane = FXMLLoader.load(getClass().getResource("..\\resources\\Register.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene registerScene = new Scene(registerPane);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
    }



}
