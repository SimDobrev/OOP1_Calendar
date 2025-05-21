package commands;

import main_classes.Program;
import parsers.LocalDateParser;
import parsers.LocalTimeParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The {@code Change} class is a command, which changes a selected value within
 * a selected meeting.
 */
public class Change implements Command {
    /**
     * Finds the meeting with the matching {@code date} and {@code startTime} and
     * if found, the old value of the given type is changed with the new given value.
     * @param args Requires values for {@code date}, {@code startTime}, {@code option} and {@code newValue}.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 4) {
            System.out.println(args.length > 4 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"change <date> <start_time> <option> <new_value>\"");
            return;
        }

        Calendar calendar = Program.getMainCalendar();

        LocalDate date = new LocalDateParser().parse(args[0]);
        if (date == null) return;
        LocalTime startTime = new LocalTimeParser().parse(args[1]);
        if (startTime == null) return;

        if (calendar.containsHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.");
            return;
        }

        if (!changeValue(calendar, date, startTime, args[2], args[3])) return;

        if (args[2].equalsIgnoreCase("date")
                || args[2].equalsIgnoreCase("starttime")
                || args[2].equalsIgnoreCase("start_time"))
            calendar.sortMeetings();

        System.out.println("Value successfully changed.");
    }

    /**
     * Finds the meeting with the matching {@code date} and {@code startTime}
     * values. If found, the method changes the selected value
     * with the new value.
     * @param calendar The given calendar.
     * @param date The date of the meeting.
     * @param startTime The meeting's start time.
     * @param option The type of the value to be changed.
     * @param newValue The new value.
     * @return {@code true} if the value is successfully changed, {@code false} if
     * the value could not be changed.
     */
    private boolean changeValue(Calendar calendar, LocalDate date, LocalTime startTime, String option, String newValue) {
        Meeting meetingToUpdate = null;
        for (Meeting meeting : calendar.getMeetings())
            if (meeting.getDate().equals(date) && meeting.getStartTime().equals(startTime))
                meetingToUpdate = meeting;
        if (meetingToUpdate == null) {
            System.out.println("Error: Meeting on " + date + " starting at " + startTime + " not found.");
            return false;
        }

        switch (option.toLowerCase()) {
            case "date": {
                LocalDate newDate = new LocalDateParser().parse(newValue);
                if (newDate == null) return false;

                if (interceptsMeeting(calendar, meetingToUpdate, newDate, meetingToUpdate.getStartTime(), meetingToUpdate.getEndTime())) {
                    System.out.println("Error: Changed meeting intercepts another meeting.");
                    return false;
                }

                meetingToUpdate.setDate(newDate);
                break;
            }
            case "starttime": case "start_time": {
                LocalTime newStartTime = new LocalTimeParser().parse(newValue);
                if (newStartTime == null) return false;

                LocalTime newEndTime = getNewEndTime(meetingToUpdate, newStartTime);
                if (interceptsMeeting(calendar, meetingToUpdate, meetingToUpdate.getDate(), newStartTime, newEndTime)) {
                    System.out.println("Error: Changed meeting intercepts another meeting, or is too late.");
                    return false;
                }

                meetingToUpdate.setStartTime(newStartTime);
                meetingToUpdate.setEndTime(newEndTime);
                break;
            }
            case "endtime": case "end_time": {
                LocalTime newEndTime = new LocalTimeParser().parse(newValue);
                if (newEndTime == null) return false;

                if (interceptsMeeting(calendar, meetingToUpdate, meetingToUpdate.getDate(), meetingToUpdate.getStartTime(), newEndTime)) {
                    System.out.println("Error: Changed meeting intercepts another meeting, or is too late.");
                    return false;
                }

                meetingToUpdate.setEndTime(newEndTime);
                break;
            }
            case "name":
                meetingToUpdate.setName(newValue.replace('_', ' '));
                break;
            case "note":
                meetingToUpdate.setNote(newValue.replace('_', ' '));
                break;
            default:
                System.out.println("Error: Invalid option.");
                System.out.println("Available options are: date, startTime (or start_time), endTime (or end_time), name, note.");
                break;
        }
        return true;
    }

    /**
     * Gets a new end proper time for a meeting when changing its start time.
     * @param meeting The meeting under change.
     * @param newStartTime The new start time.
     * @return The new end time.
     */
    private LocalTime getNewEndTime(Meeting meeting, LocalTime newStartTime) {
        LocalTime oldStartTime = meeting.getStartTime();
        LocalTime oldEndTime = meeting.getEndTime();
        LocalTime meetingLength = oldEndTime
                .minusHours(oldStartTime.getHour())
                .minusMinutes(oldStartTime.getMinute())
                .minusSeconds(oldStartTime.getSecond());

        return newStartTime
                .plusHours(meetingLength.getHour())
                .plusMinutes(meetingLength.getMinute())
                .plusSeconds(meetingLength.getSecond());
    }

    /**
     * Checks whether the changes to the meeting would intercept another meeting
     * within the given calendar.
     * @param calendar The given calendar.
     * @param meetingToUpdate The meeting to be updated.
     * @param date The old/new date.
     * @param startTime The old/new start time.
     * @param endTime The old/new end time.
     * @return {@code true} if the changes to the meeting intercept another meeting,
     * {@code false} if the new values fit the schedule.
     */
    private boolean interceptsMeeting(Calendar calendar, Meeting meetingToUpdate, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Meeting> meetingsOnNewDate = calendar.getMeetingsPerDay().get(date);
        if (meetingsOnNewDate == null) return false;

        for (Meeting meeting : meetingsOnNewDate) {
            if (meeting.equals(meetingToUpdate))
                continue;

            if (startTime.isBefore(meeting.getStartTime()))
                return endTime.isAfter(meeting.getStartTime());

            if (!startTime.isBefore(meeting.getStartTime())
                    && startTime.isBefore(meeting.getEndTime()))
                return true;
        }

        return true;
    }
}
