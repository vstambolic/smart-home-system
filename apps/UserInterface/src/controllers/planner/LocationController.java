package controllers.planner;

import controllers.utils.Controller;
import controllers.utils.LocationParentController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class LocationController extends Controller {
    private WebView wv = new WebView();
    private WebEngine webEngine = wv.getEngine();
    private LocationParentController parentController;

    @FXML
    private void initialize() {
        /*webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            if (LocationController.this.eventController != null) {
                                System.out.println(eventController.getLatitude());
                                webEngine.executeScript("setLatitude(" + eventController.getLatitude()+")");
                                webEngine.executeScript("setLongitude("+ eventController.getLongitude()+")");
                                webEngine.executeScript("loadMapScenario()");
                                System.out.println("aa");
                            }
                        }
                    }
                });*/
        webEngine.load(getClass().getResource("..\\..\\resources\\planner\\bingMaps.html").toString());
        hb.getChildren().add(wv);
    }

    @FXML
    private VBox locationBox;

    @FXML
    private HBox hb;

    private String locationName;


    public void setLocation(String locationName) {
        this.locationName = locationName;
    }

    @FXML
    void cancelLocationAction(ActionEvent event) {
        System.out.println(this.locationName);
        this.showNewEventPage();
    }



    @FXML
    void saveLocationAction(ActionEvent event) {

        String address = (String) this.webEngine.executeScript("getAddress()");
        Double lat = (Double) this.webEngine.executeScript("getLatitude()");
        Double lon = (Double) this.webEngine.executeScript("getLongitude()");
        System.out.println(address + " " + lat + " " + lon);


            this.parentController.setAddress(address);
            this.parentController.setLatitude(lat.doubleValue());
            this.parentController.setLongitude(lon.doubleValue());
            this.showNewEventPage();

    }

    private void showNewEventPage() {
        StackPane sp = (StackPane) this.locationBox.getParent();

        locationBox.translateXProperty().set(0);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(locationBox.translateXProperty(), 1.5*sp.getWidth(), Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        sp.getChildren().get(0).setVisible(true);
    }


    public void setParentController(LocationParentController controller) {
        this.parentController = controller;
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            if (LocationController.this.parentController != null) {

                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("setParameters(");
                                stringBuilder.append("'" + parentController.getAddress()+"'");
                                stringBuilder.append(",");
                                stringBuilder.append(parentController.getLatitude());
                                stringBuilder.append(",");
                                stringBuilder.append(parentController.getLongitude());
                                stringBuilder.append(")");
                                webEngine.executeScript(stringBuilder.toString());
                                webEngine.executeScript("printOutPanel()");
                                webEngine.executeScript("loadMapScenario()");

                            }
                        }
                    }
                });
    }
}
