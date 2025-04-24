package main_classes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Calendar {
    private String calendarName;
    private Set<Meeting> meetings = new HashSet<>();
    private Set<Date> holidays = new HashSet<>();

    public Calendar(String calendarName) {
        this.calendarName = calendarName;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public Set<Date> getHolidays() {
        return holidays;
    }

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
}
