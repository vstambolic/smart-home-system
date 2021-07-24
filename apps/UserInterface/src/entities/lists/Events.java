package entities.lists;

import entities.Event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "events")
@XmlAccessorType(XmlAccessType.FIELD)
public class Events {

    @XmlElement(name = "event")
    private List<Event> events = null;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> alarms) {
        this.events = events;
    }
}
