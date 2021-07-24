package eventmanager.services;

import entities.Event;
import entities.Track;
import entities.User;
import eventmanager.EventManager;

import java.util.ArrayList;

public class FetchEventsService extends Service{

    private User user;
    public FetchEventsService(Integer userID) {
        this.user = EventManager.EM.find(User.class, userID);
        EventManager.EM.refresh(user);



    }

    @Override
    public Object execute() {

        if (user.getEventList() != null)
            System.out.println("events" + user.getEventList().size());

        ArrayList<Event> eventList = new ArrayList(user.getEventList());
        return eventList;
    }
}
