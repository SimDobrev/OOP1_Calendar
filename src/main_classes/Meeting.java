package main_classes;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Every object of the {@code Meeting} class is one of the many that can be saved
 * into one or many calendars. The class does not have a default constructor, but a
 * meeting can be constructed with values for {@code date}, {@code startTime},
 * {@code endTime}, {@code name} and {@code note}.
 */
public class Meeting {
    /**
     * The date of the meeting.
     */
    private LocalDate date;
    /**
     * The meeting's start time.
     */
    private LocalTime startTime;
    /**
     * The meeting's end time.
     */
    private LocalTime endTime;
    /**
     * The person/group the meeting is with.
     */
    private String name;
    /**
     * A note for the meeting.
     */
    private String note;

    /**
     * Constructs a new {@code Meeting} with the given attributes.
     * @param date The date of the meeting.
     * @param startTime The meeting's start time.
     * @param endTime The meeting's end time.
     * @param name The person/group the meeting is with.
     * @param note A note for the meeting.
     */
    public Meeting(LocalDate date, LocalTime startTime, LocalTime endTime, String name, String note) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.note = note;
    }

    /**
     * Gets the date of this meeting.
     * @return The date of this meeting.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the start time of this meeting.
     * @return The start time of this meeting.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of this meeting.
     * @return The end time of this meeting.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the name of this meeting.
     * @return The name of this meeting.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the note of this meeting.
     * @return The note of this meeting.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets this meeting's date to the given date.
     * @param date The given date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets this meeting's start time to the given start time.
     * @param startTime The given start time.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets this meeting's end time to the given end time.
     * @param endTime The given end time.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets this meeting's name to the given name.
     * @param name The given name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets this meeting's note to the given note.
     * @param note The given note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets and returns this meeting's information in a custom format.
     * @return This meeting's information in a custom format.
     */
    public String getDescription() {
        return "Meeting with " + name + " on " + date + ".\n" +
                "Starts at " + startTime + ", ends at " + endTime + ".\n" +
                "Note: " + note;
    }

    /**
     * Gets and returns this meeting's information in a generic format.
     * @return This meeting's information in a generic format.
     */
    @Override
    public String toString() {
        return "Date: " + date +
                "\nStart time: " + startTime +
                "\nEnd time: " + endTime +
                "\nName: " + name +
                "\nNote: " + note;
    }
}
