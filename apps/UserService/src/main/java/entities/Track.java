/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vstambolic
 */
@Entity
@Table(name = "track")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Track.findAll", query = "SELECT t FROM Track t"),
    @NamedQuery(name = "Track.findBySpotifyURI", query = "SELECT t FROM Track t WHERE t.spotifyURI = :spotifyURI"),
    @NamedQuery(name = "Track.findByName", query = "SELECT t FROM Track t WHERE t.name = :name"),
    @NamedQuery(name = "Track.findByArtist", query = "SELECT t FROM Track t WHERE t.artist = :artist"),
    @NamedQuery(name = "Track.findByAlbum", query = "SELECT t FROM Track t WHERE t.album = :album"),
    @NamedQuery(name = "Track.findByYear", query = "SELECT t FROM Track t WHERE t.year = :year")})
public class Track implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "artist")
    private String artist;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "album")
    private String album;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "spotifyURI")
    private String spotifyURI;
    @JoinTable(name = "user_track", joinColumns = {
        @JoinColumn(name = "idTrack", referencedColumnName = "spotifyURI")}, inverseJoinColumns = {
        @JoinColumn(name = "idUser", referencedColumnName = "idUser")})
    @ManyToMany
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTrack")
    private List<Alarm> alarmList;

    public Track() {
    }

    public Track(String spotifyURI) {
        this.spotifyURI = spotifyURI;
    }

    public Track(String spotifyURI, String name, String artist, String album, int year) {
        this.spotifyURI = spotifyURI;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    public String getSpotifyURI() {
        return spotifyURI;
    }

    public void setSpotifyURI(String spotifyURI) {
        this.spotifyURI = spotifyURI;
    }


    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spotifyURI != null ? spotifyURI.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Track)) {
            return false;
        }
        Track other = (Track) object;
        if ((this.spotifyURI == null && other.spotifyURI != null) || (this.spotifyURI != null && !this.spotifyURI.equals(other.spotifyURI))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Track[ spotifyURI=" + spotifyURI + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
}
