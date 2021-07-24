package controllers.users;

import controllers.planner.LocationController;
import controllers.utils.Controller;
import controllers.utils.LocationParentController;
import entities.User;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.httpConnection.HttpRequestBodyReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController extends LocationParentController {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private String homeAddress="Bulevar kralja Aleksandra 73, 11120 Belgrade, Serbia";
    private double latitude=44.80551;
    private double longitude=20.47611;
    private boolean locationSet=false;

    @FXML
    private StackPane stackPane;
    @FXML
    private VBox registerBox;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button locationButton;

    @FXML
    private Label messageLabel;

    @FXML
    void cancelAction(ActionEvent event) {

        Parent loginPane = null;
        try {
            loginPane = FXMLLoader.load(getClass().getResource("..\\..\\resources\\LogIn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene registerScene = new Scene(loginPane);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
    }


    private boolean preCheck() {
        if (!this.checkTextFields()) {
            messageLabel.setText("Text fields must not be empty.");
            return false;
        }
        if (!this.checkUsernameLength()) {
            messageLabel.setText("Usernames must be between 4 and 25 characters.");
            return false;
        }

        if (!this.checkPasswordLength()) {
            messageLabel.setText("Passwords must be between 4 and 25 characters.");
            return false;
        }
        if (!checkEmailFormat(emailTextField.getText())) {
            messageLabel.setText("Invalid e-mail address.");
            return false;
        }
        if (!this.locationSet) {
            messageLabel.setText("Select your home address.");
            return false;
        }

        this.messageLabel.setText("");
        return true;

    }



    private boolean registerUser() {
        try {

            Map<String,Object> params = new LinkedHashMap<>();
            params.put("firstName", this.firstNameTextField.getText());
            params.put("lastName", this.lastNameTextField.getText());
            params.put("email", this.emailTextField.getText());
            params.put("username", this.usernameTextField.getText());
            params.put("password", this.passwordTextField.getText());
            params.put("homeAddress",this.homeAddress);
            params.put("homeLatitude",this.latitude);
            params.put("homeLongitude",this.longitude);


            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/UserService/s/users/register").openConnection());
            connection.setRequestMethod("POST");
            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                JAXBContext.newInstance(User.class).createUnmarshaller().unmarshal( new StreamSource( new StringReader( HttpRequestBodyReader.readInputStream(connection))));
                return true;
            }
            if (responseCode == HttpURLConnection.HTTP_CONFLICT)
                messageLabel.setText(HttpRequestBodyReader.readErrorStream(connection));

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return false;
    }
    @FXML
    void registerAction(ActionEvent event) {
        if (!this.preCheck())
            return;
        if (!this.registerUser()) {
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(666);
            } catch (InterruptedException exc) {
                throw new Error("Unexpected interruption", exc);
            }
            Platform.runLater(() -> {
                Parent home = null;
                try {
                    home = FXMLLoader.load(getClass().getResource("..\\..\\resources\\Home.fxml"));
                    ((BorderPane)home).setCenter(FXMLLoader.load(getClass().getResource("..\\..\\resources\\HomeMenu.fxml")));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene homeScene = new Scene(home);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(homeScene);});
        });
        thread.setDaemon(true);
        thread.start();
        messageLabel.setText("Welcome " + "user!");

    }



    private boolean checkUsernameLength() {
        return this.usernameTextField.getLength()>=4 && this.usernameTextField.getLength()<=25;
    }
    private boolean checkPasswordLength() {
        return this.passwordTextField.getLength()>=4 && this.passwordTextField.getLength()<=25;
    }

    private boolean checkUsernameAvailable() {
        /*TODO*/
        return true;
    }

    private boolean checkLocation() {
        return this.homeAddress!=null;
    }


    private static boolean checkEmailFormat(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


    private static boolean checkTextFieldLength(TextField textField) {
        return textField.getLength() != 0;
    }
    private boolean checkTextFields() {
        return checkTextFieldLength(firstNameTextField) && checkTextFieldLength(lastNameTextField) && checkTextFieldLength(emailTextField)
                && checkTextFieldLength(usernameTextField) &&checkTextFieldLength(passwordTextField);
    }




    @FXML
    void locationAction(ActionEvent event) {
        try {
            stackPane.getChildren().remove(1);
        }
        catch (IndexOutOfBoundsException e) {

        }



        VBox locationHome = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\resources\\Location.fxml"));
            locationHome = loader.load();
            LocationController lc = loader.getController();
            lc.setParentController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }


        locationHome.translateXProperty().set(stackPane.getWidth());

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(locationHome.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        this.stackPane.getChildren().add(locationHome);
        this.registerBox.setVisible(false);
    }


    @Override
    public void setAddress(String address) {
        this.locationButton.setText(homeAddress=address);
        locationSet=true;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude=latitude;

    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude=longitude;

    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public String getAddress() {
        return this.homeAddress;
    }
}
