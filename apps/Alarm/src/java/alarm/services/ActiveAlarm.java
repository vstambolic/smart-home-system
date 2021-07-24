package alarm.services;

import alarm.AlarmManager;
import entities.Alarm;
import entities.Event;
import entities.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.ejb.Local;
import javax.persistence.Query;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class ActiveAlarm extends Thread {

    private final Alarm alarm;
    private static List<ActiveAlarm> activeAlarmList = new ArrayList<>();


    public ActiveAlarm(Alarm alarm) {
        this.alarm = alarm;
        activeAlarmList.add(this);
    }


    public static List<ActiveAlarm> getActiveAlarmList() {
        return activeAlarmList;
    }


    @Override
    public void run() {
        System.out.println("Active alarm: " + this.alarm.getLabel() + " has started.");

        long sleepTime = getSleepTime(alarm);
        System.out.println(sleepTime);
        try {

            while (true) {
                Thread.sleep(sleepTime);

                if (this.isEventAlarm())
                    this.setCurrentEvent();
                else
                    Platform.runLater(() -> AlarmManager.showAlert(this.alarm));


                System.out.println("RING");
                if (alarm.getRepetitive() == 0) {
                    alarm.setActive((short) 0);
                    AlarmManager.EM.getTransaction().begin();
                    AlarmManager.EM.persist(alarm);
                    AlarmManager.EM.getTransaction().commit();
                    AlarmManager.EM.clear();

                    break;

                } else
                    sleepTime = 8_640_000;
            }
        }
        catch (InterruptedException e) {

        }
    }

    private boolean isEventAlarm() {
        Event event = this.alarm.getEvent();
        return event != null && event.getTime().equals(alarm.getTime()) && event.getDate().equals(alarm.getDate());
    }

    private void setCurrentEvent() {
        User user = this.alarm.getIdUser();
        AlarmManager.EM.getTransaction().begin();

             user.setCurrEvent(this.alarm.getEvent());
             Query query = AlarmManager.EM.createQuery("UPDATE User u SET u.currEvent = :eventID WHERE u.idUser = :userID");
             query.setParameter("eventID",this.alarm.getEvent());
             query.setParameter("userID",user.getIdUser());
             query.executeUpdate();
             AlarmManager.EM.remove(alarm);

        AlarmManager.EM.getTransaction().commit();
        AlarmManager.EM.clear();
    }


    public static LocalTime getAlarmTime(Alarm alarm) {
        Date alarmTime = alarm.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(alarmTime);
        return LocalTime.of( calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
    }

    public static LocalDate getAlarmDate(Alarm alarm) {
        Date alarmDate = alarm.getDate();
        if (alarmDate == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(alarmDate);
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH));

    }

    public static LocalDateTime getAlarmDateTime(Alarm alarm) {
        return LocalDateTime.of(getAlarmDate(alarm),getAlarmTime(alarm));
    }

    private static long getSleepTime(Alarm alarm) {
        LocalDateTime justNow = LocalDateTime.now();

        LocalTime alarmTime = getAlarmTime(alarm);
        LocalDate alarmDate = getAlarmDate(alarm);

        LocalDateTime alarmDateTime;

        if (alarmDate == null) {
           LocalDate todayDate = LocalDate.now();
           LocalDateTime todayDateTime = LocalDateTime.of(todayDate,alarmTime);

           if (justNow.isBefore(todayDateTime))
               alarmDateTime = todayDateTime;
           else
               alarmDateTime = LocalDateTime.from(todayDateTime).plusDays(1);
        }
        else
            alarmDateTime = LocalDateTime.of(alarmDate,alarmTime);

        return Timestamp.valueOf(alarmDateTime).getTime() - Timestamp.valueOf(justNow).getTime();
    }


    public Alarm getAlarm() {
        return this.alarm;
    }
}
