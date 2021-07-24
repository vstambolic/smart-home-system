package alarm.services;

import alarm.AlarmManager;
import entities.Alarm;

public class SwitchAlarmService extends Service {

    private Alarm alarm;

    public SwitchAlarmService(int alarmId) {
         this.alarm = AlarmManager.EM.find(Alarm.class,alarmId);
    }

    @Override
    public Object execute() {
        ActiveAlarm activeAlarmToSwitch = ActiveAlarm.getActiveAlarmList()
                .stream()
                .filter(activeAlarm -> activeAlarm.getAlarm().equals(alarm))
                .findFirst()
                .get();

        if (alarm.getActive() == 1) {
            ActiveAlarm.getActiveAlarmList().remove(activeAlarmToSwitch);
            new SetNewAlarmService(alarm).execute();
        }
        else
            activeAlarmToSwitch.interrupt();

        return null;
    }
}
