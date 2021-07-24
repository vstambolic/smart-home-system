package services;

import entities.Track;
import entities.User;
import java.util.ArrayList;

import java.util.List;


import musicplayer.*;

public class GetTracksService extends Service {

    private int userID;


    
    public GetTracksService(Integer userID) {
        this.userID = userID;
    }

    @Override
    public List<Track> execute() {
         User user = MusicPlayer.EM.find(User.class, userID);
         MusicPlayer.EM.refresh(user);

         System.out.println("user: " + user);
         System.out.println("list:" + user.getTrackList());
         if (user.getTrackList() != null)
             System.out.println("tracks" + user.getTrackList().size());
         
         ArrayList<Track> trackList = new ArrayList(user.getTrackList());
         return trackList;
    }

}
