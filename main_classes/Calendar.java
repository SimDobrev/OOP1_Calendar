package main_classes;

import java.sql.Date;
import java.util.*;

public class Calendar {
    private static Calendar calendar = null;
    private String name;
    private Set<Meeting> meetings = new HashSet<>();
    private Set<Date> holidays = new HashSet<>();

    public static Calendar get() {
        return calendar;
    }

    public static void set(Calendar instance) {
        calendar.name = instance == null ? null : instance.getName();
        calendar = instance;
    }

    private Calendar() {}

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    public void addHoliday(Date date) {
        holidays.add(date);
    }

    public void removeHoliday(Date date) {
        holidays.remove(date);
    }
    public boolean isHoliday(Date date) {
        return holidays.contains(date);
    }

    public String getName() {
        return name;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public Set<Date> getHolidays() {
        return holidays;
    }
}
