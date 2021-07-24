package entities.lists;


import entities.Alarm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "alarms")
@XmlAccessorType(XmlAccessType.FIELD)
public class Alarms {

    @XmlElement(name = "alarm")
    private List<Alarm> alarms = null;

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
