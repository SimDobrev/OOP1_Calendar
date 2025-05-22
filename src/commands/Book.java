package commands;

import main_classes.Program;
import parsers.LocalTimeParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;
import parsers.LocalDateParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * The {@code Book} class is a command, which books a {@code Meeting} and adds it
 * to the currently open calendar's meetings set.
 */
public class Book implements Command {
    /**
     * Checks whether a meeting can be booked within the given date's schedule.
     * If it can, a {@code Meeting} is created with the given values and added to
     * the currently open calendar's meetings set, otherwise an appropriate error
     * message is printed.
     * @param args Requires values for {@code date}, {@code startTime},
     * {@code endTime} and {@code name}. A value for {@code note} is not
     * required, but could be given.
     */
    @Override
    public void execute(String... args) {
        if (args.length < 4 || args.length > 5) {
            System.out.println(args.length > 5 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"book <date> <start_time> <end_time> <name> <note>\"");
            return;
        }

        Calendar calendar = Program.getMainCalendar();

        LocalDate date = new LocalDateParser().parse(args[0]);
        if (date == null) return;
        LocalTime startTime = new LocalTimeParser().parse(args[1]);
        if (startTime == null) return;
        LocalTime endTime = new LocalTimeParser().parse(args[2]);
        if (endTime == null) return;

        if (!canBookMeeting(date, startTime, endTime)) return;

        if (args[3].isEmpty()) {
            System.out.println("Error: No name given.");
            return;
        }

        Meeting newMeeting = new Meeting(date, startTime, endTime,
                args[3].replace('_', ' '),
                args.length == 5 ? args[4].replace('_', ' ') : "");

        if (!calendar.containsMeeting(newMeeting)) {
            calendar.addMeeting(newMeeting);
            calendar.sortMeetings();
            Program.setMainCalendar(calendar);
            System.out.println("New meeting successfully booked.");
        }
        else System.out.println("Error: Meeting has already been booked.");
    }

    /**
     * Checks whether the given date is a holiday, the start and end times are valid.
     * @param date The given date.
     * @param startTime The proposed start time.
     * @param endTime The proposed end time.
     * @return {@code true} if the given values are valid, {@code false} if they are not.
     */
    private boolean canBookMeeting(LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (Program.getMainCalendar().containsHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.");
            return false;
        }

        if (!startTime.isBefore(endTime)) {
            System.out.println("Error: Starting time cannot be later than or equal to the end time.");
            return false;
        }

        if (!areValidTimes(date, startTime, endTime)) {
            System.out.println("Error: The given times do not fit within that day's schedule.");
            return false;
        }

        return true;
    }

    /**
     * Checks whether a meeting with the given start and end times fits within
     * the given date's schedule. Returns {@code true} if it fits, {@code false}
     * if not.
     * @param date The given date.
     * @param startTime The proposed start time.
     * @param endTime The proposed end time.
     * @return {@code true} if the meeting fits within the schedule, {@code false}
     * if not.
     */
    private boolean areValidTimes(LocalDate date, LocalTime startTime, LocalTime endTime) {
        Map<LocalDate, List<Meeting>> meetingsPerDay = Program.getMainCalendar().getMeetingsPerDay();
        if (!meetingsPerDay.containsKey(date)) return true;

        int index = 1;
        for (Meeting meeting : meetingsPerDay.get(date)) {
            if (startTime.isBefore(meeting.getStartTime()))
                return !endTime.isAfter(meeting.getStartTime());

            if (!startTime.isBefore(meeting.getEndTime())) {
                if (index == meetingsPerDay.get(date).size()) return true;
                else index++;
            }
            else break;
        }
        return false;
    }
}
