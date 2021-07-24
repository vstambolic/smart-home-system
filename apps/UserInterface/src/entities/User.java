package entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class User implements Serializable {

    private static User CURRENT_USER =null;
    public static User getCurrentUser() {
        return CURRENT_USER;
    }


    private static final long serialVersionUID = 1L;
    private Integer idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String homeAddress;
    private double homeLatitude;
    private double homeLongitude;


    private Event currEvent;


    public User() {
        CURRENT_USER = this;
    }

    public static void createNewUser(Integer idUser, String firstName, String lastName, String email, String username, String password, String homeAddress, double homeLatitude, double homeLongitude) {
        CURRENT_USER = new User(idUser,firstName,lastName,email,username,password, homeAddress, homeLatitude, homeLongitude);
    }

    public User(Integer idUser, String firstName, String lastName, String email, String username, String password, String homeAddress, double homeLatitude, double homeLongitude) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.homeAddress = homeAddress;
        this.homeLatitude = homeLatitude;
        this.homeLongitude = homeLongitude;

    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public double getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(double homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public double getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(double homeLongitude) {
        this.homeLongitude = homeLongitude;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ idUser=" + idUser + " ]";
    }


    public Event getCurrEvent() {
        return currEvent;
    }
    public void setCurrEvent(Event currEvent) {
        this.currEvent = currEvent;
    }
}
