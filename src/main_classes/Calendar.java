package main_classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Every object of the {@code Calendar} class, has a unique name, a private
 * {@code Set<Meeting>} meetings and a private {@code Set<LocalDate} holidays.
 * The class does not have a default constructor, but a calendar can be
 * constructed with a given value for the calendar's {@code name}.
 */
public class Calendar {
    /**
     * The name of the calendar.
     */
    private String name;
    /**
     * The {@code Set<Meeting>} containing all meetings.
     */
    private Set<Meeting> meetings = new LinkedHashSet<>();
    /**
     * The {@code Set<LocalDate>} containing all holidays.
     */
    private Set<LocalDate> holidays = new LinkedHashSet<>();


    /**
     * Constructs a new {@code Calendar} with the given name, an empty
     * set of meetings {@code Set<Meetings>} and an empty set of
     * holidays {@code Set<LocalDate>}.
     * @param name The calendar name.
     */
    public Calendar(String name) {
        this.name = name;
    }

    /**
     * Adds the given meeting to this calendar's set of meetings.
     * @param meeting The meeting to be added.
     */
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Removes the given meeting from this calendar's set of meetings.
     * @param meeting The meeting to be removed.
     */
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * Checks whether this calendar's meetings set contains the given meeting.
     * @param meeting The given meeting.
     * @return {@code True} if the meeting is found, {@code false} if not found.
     */
    public boolean containsMeeting(Meeting meeting) {
        return meetings.contains(meeting);
    }

    /**
     * Adds the given holiday to this calendar's set of holidays.
     * @param date The holiday to be added.
     */
    public void addHoliday(LocalDate date) {
        holidays.add(date);
    }

    /**
     * Removes the given holiday from this calendar's set of holidays.
     * @param date The holiday to be removed.
     */
    public void removeHoliday(LocalDate date) {
        holidays.remove(date);
    }

    /**
     * Checks whether this calendar's holidays set contains the given date.
     * @param date The given date.
     * @return {@code True} if the date is found, {@code false} if not found.
     */
    public boolean containsHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    /**
     * Gets the name of this calendar.
     * @return The name of this calendar.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this calendar's set of meetings {@code Set<Meeting>}.
     * @return This calendar's set of meetings {@code Set<Meeting>}.
     */
    public Set<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * Gets this calendar's set of holidays {@code Set<LocalDate>}.
     * @return This calendar's set of holidays {@code Set<LocalDate>}.
     */
    public Set<LocalDate> getHolidays() {
        return holidays;
    }

    /**
     * Sorts the meetings set in ascending order, first
     * comparing the date, then comparing the start time.
     */
    public void sortMeetings() {
        List<Meeting> meetings = new ArrayList<>(this.meetings);
        meetings.sort(Comparator
                .comparing(Meeting::getDate)
                .thenComparing(Meeting::getStartTime));
        this.meetings = new LinkedHashSet<>(meetings);
    }

    /**
     * Sorts the {@code Set<LocalDate>} of holidays in ascending order.
     */
    public void sortHolidays() {
        List<LocalDate> holidays = new ArrayList<>(this.holidays);
        holidays.sort(LocalDate::compareTo);
        this.holidays = new LinkedHashSet<>(holidays);
    }

    /**
     * Gets all meetings and orders them into separate lists by date.
     * @return A map with dates as keys and lists of all meetings on the
     * corresponding date as values.
     */
    public Map<LocalDate, List<Meeting>> getMeetingsPerDay() {
        Map<LocalDate, List<Meeting>> meetingsPerDay = new LinkedHashMap<>();
        for (Meeting meeting : getMeetings()) {
            if (meetingsPerDay.containsKey(meeting.getDate()))
                meetingsPerDay.get(meeting.getDate()).add(meeting);
            else {
                meetingsPerDay.put(meeting.getDate(), new ArrayList<>());
                meetingsPerDay.get(meeting.getDate()).add(meeting);
            }
        }
        return meetingsPerDay;
    }
}
