package entities;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vstambolic
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idAlarm;
    private String label;
    private Date time;
    private short repetitive;
    private short active;
    private Date date;
    private Track idTrack;
    private User idUser;
    private Event event;


    public Alarm() {
    }

    public Alarm(Integer idAlarm) {
        this.idAlarm = idAlarm;
    }

    public Alarm(Integer idAlarm, String label, Date time, short repetitive, short active) {
        this.idAlarm = idAlarm;
        this.label = label;
        this.time = time;
        this.repetitive = repetitive;
        this.active = active;
    }

    public Integer getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(Integer idAlarm) {
        this.idAlarm = idAlarm;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public short getRepetitive() {
        return repetitive;
    }

    public void setRepetitive(short repetitive) {
        this.repetitive = repetitive;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Track getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(Track idTrack) {
        this.idTrack = idTrack;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlarm != null ? idAlarm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alarm)) {
            return false;
        }
        Alarm other = (Alarm) object;
        if ((this.idAlarm == null && other.idAlarm != null) || (this.idAlarm != null && !this.idAlarm.equals(other.idAlarm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Alarm[ idAlarm=" + idAlarm + " ]";
    }

}
