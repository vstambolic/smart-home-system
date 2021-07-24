package alarm.services;

import alarm.AlarmManager;
import entities.Alarm;

import javax.persistence.EntityManager;

public class SetNewAlarmService extends Service{

    private ActiveAlarm activeAlarm;


    public SetNewAlarmService(Integer alarmId) {
        this.activeAlarm = new ActiveAlarm(AlarmManager.EM.find(Alarm.class,alarmId));
    }

    public SetNewAlarmService(Alarm alarm) {
        this.activeAlarm = new ActiveAlarm(alarm);
    }

    @Override
    public Object execute() {
        this.activeAlarm.start();
        return null;
    }
}
