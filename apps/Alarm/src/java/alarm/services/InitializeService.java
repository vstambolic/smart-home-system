package alarm.services;

import alarm.AlarmManager;
import entities.Alarm;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InitializeService extends Service{

    private User user;

    public InitializeService(Integer userID) {
        this.user = AlarmManager.EM.find(User.class,userID);
    }

    @Override
    public Object execute() {

        List<Alarm> alarmList = user.getAlarmList();
        if (alarmList!=null && !alarmList.isEmpty()) {

            LocalDateTime justNow = LocalDateTime.now();

            alarmList.stream()
                    .filter(alarm -> alarm.getActive() == 1)
                    .collect(Collectors.toList());

            AlarmManager.EM.getTransaction().begin();

            alarmList.removeIf(alarm -> {
                boolean toRemove = alarm.getDate() != null && ActiveAlarm.getAlarmDateTime(alarm).isBefore(justNow);
                if (toRemove) {
                   AlarmManager.EM.remove(alarm);
                }
                return toRemove;
            });

            AlarmManager.EM.getTransaction().commit();
            AlarmManager.EM.clear();


            alarmList.forEach(alarm -> new SetNewAlarmService(alarm).execute());
        }
        return null;
    }
}
