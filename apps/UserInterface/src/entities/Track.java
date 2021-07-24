/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author vstambolic
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    private String spotifyURI;
   private String name;
   private String artist;
    private String album;
   private int year;

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
    
}
