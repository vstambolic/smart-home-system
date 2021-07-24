package utils.ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SwitchButton  extends HBox {
    private final Label label = new Label();
    private final Button button = new Button();
    private final Method method;
    private final Object object;

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty getSwitchedOn() { return switchedOn; }
    public void setSwitchedOn(boolean val) {
        switchedOn.set(val);
        if (val)
            button.toFront();
        else
            label.toFront();


    }
    public Label getLabel() {
        return this.label;
    }
    public Button getButton() {
        return this.button;
    }

    private void init() {
        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            switchedOn.set(!switchedOn.get());
            if (this.method!=null)
                try {
                    method.invoke(object);
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (InvocationTargetException invocationTargetException) {
                    invocationTargetException.printStackTrace();
                }

        });
        label.setOnMouseClicked((e) -> {
            switchedOn.set(!switchedOn.get());
            if (this.method!=null)
                try {
                    method.invoke(object);
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (InvocationTargetException invocationTargetException) {
                    invocationTargetException.printStackTrace();
                }
        });
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        //Default Width
        this.setPrefWidth(60);
        this.setMaxWidth(60);
        this.setPrefHeight(30);
        this.setMinHeight(30);
        this.setMaxHeight(30);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #171C2B; -fx-background-radius: 16;-fx-cursor: hand;");
        label.setStyle("-fx-background-radius:16;");
        button.setStyle("-fx-background-radius:30; -fx-background-color:white");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());

        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public SwitchButton(Method method, Object object) {
        this.object = object;
        this.method = method;
        init();
        switchedOn.addListener((a,b,c) -> {
            if (c)                button.toFront();

            else
            label.toFront();

        });
    }
}