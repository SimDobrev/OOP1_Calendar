package commands;

import main_classes.Program;
import parsers.LocalDateParser;
import parsers.LocalTimeParser;
import interfaces.Command;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code Unbook} class is a command, which unbooks a meeting from the currently
 * open calendar, if it exists.
 */
public class Unbook implements Command {
    /**
     * Finds the {@code Meeting} with the given values and if it exists, it gets unbooked,
     * if not, an appropriate error message is printed.
     * @param args Requires values for {@code date}, {@code startTime} and
     * {@code endTime}.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 3) {
            System.out.println(args.length > 3 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"unbook <date> <start_time> <end_time>\"");
            return;
        }

        if (Program.getMainCalendar().getMeetings().isEmpty()) {
            System.out.println("Error: Calendar has no booked meetings.");
            return;
        }

        LocalDate date = new LocalDateParser().parse(args[0]);
        if (date == null) return;
        LocalTime startTime = new LocalTimeParser().parse(args[1]);
        if (startTime == null) return;
        LocalTime endTime = new LocalTimeParser().parse(args[2]);
        if (endTime == null) return;

        if (Program.getMainCalendar().containsHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.");
            return;
        }

        if (!startTime.isBefore(endTime)) {
            System.out.println("Error: Starting time cannot be greater than or equal to the end time.");
            return;
        }

        if (!Program.getMainCalendar().getMeetings().removeIf(
                meeting -> meeting.getDate().equals(date) &&
                        meeting.getStartTime().equals(startTime) &&
                        meeting.getEndTime().equals(endTime))) {
            System.out.println("Error: Meeting not found.");
            return;
        }

        System.out.println("Meeting successfully unbooked.");
    }
}
