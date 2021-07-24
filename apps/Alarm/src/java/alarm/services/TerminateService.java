package alarm.services;

public class TerminateService extends Service{


    @Override
    public Object execute() {
        ActiveAlarm.getActiveAlarmList().forEach(activeAlarm -> activeAlarm.interrupt());
        ActiveAlarm.getActiveAlarmList().clear();
        return null;
    }
}
