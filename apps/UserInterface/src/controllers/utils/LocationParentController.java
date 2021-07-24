package controllers.utils;

import javafx.scene.control.Button;

public abstract  class LocationParentController extends Controller{

    public abstract void setAddress(String address);
    public abstract void setLatitude(double latitude);
    public abstract void setLongitude(double longitude);
    public abstract double getLongitude();
    public abstract double getLatitude();
    public abstract String getAddress();
}
