/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vaso
 */
@Entity
@Table(name = "alarm")
@NamedQueries({
    @NamedQuery(name = "Alarm.findAll", query = "SELECT a FROM Alarm a"),
    @NamedQuery(name = "Alarm.findByIdAlarm", query = "SELECT a FROM Alarm a WHERE a.idAlarm = :idAlarm"),
    @NamedQuery(name = "Alarm.findByLabel", query = "SELECT a FROM Alarm a WHERE a.label = :label"),
    @NamedQuery(name = "Alarm.findByTime", query = "SELECT a FROM Alarm a WHERE a.time = :time"),
    @NamedQuery(name = "Alarm.findByRepetitive", query = "SELECT a FROM Alarm a WHERE a.repetitive = :repetitive"),
    @NamedQuery(name = "Alarm.findByActive", query = "SELECT a FROM Alarm a WHERE a.active = :active"),
    @NamedQuery(name = "Alarm.findByDate", query = "SELECT a FROM Alarm a WHERE a.date = :date")})
public class Alarm implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "label")
    private String label;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repetitive")
    private short repetitive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private short active;
    @JoinColumn(name = "event", referencedColumnName = "idEvent")
    @ManyToOne
    private Event event;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAlarm")
    private Integer idAlarm;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "idTrack", referencedColumnName = "spotifyURI")
    @ManyToOne(optional = false)
    private Track idTrack;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idUser;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
}
