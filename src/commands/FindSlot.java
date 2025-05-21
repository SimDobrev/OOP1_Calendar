package commands;

import abstract_classes.SlotFinder;
import main_classes.Meeting;
import main_classes.Program;
import parsers.LocalDateParser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code FindSlot} class is a command, which finds an available slot for a
 * meeting, starting from a given date.
 */
public class FindSlot extends SlotFinder {
    /**
     * Finds an available slot for a meeting starting from a given date, a given
     * amount of hours long, within the currently open calendar.
     * @param args Requires values for a starting date {@code from_date} and the
     * length of the meeting in {@code hours}.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"findSlot <from_date> <hours>\"");
            return;
        }

        LocalDate fromDate = new LocalDateParser().parse(args[0]);
        if (fromDate == null) return;

        int hours;
        try {
            hours = Integer.parseInt(args[1]);
            if (hours < 1 || hours > 9) {
                System.out.println("Error: A meeting can be minimum 1 hour and maximum 9 hours long.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entered value is not an integer.");
            return;
        }

        Map<LocalDate, List<Meeting>> meetingsPerDay = Program.getMainCalendar().getMeetingsPerDay();
        Set<LocalDate> holidays = Program.getMainCalendar().getHolidays();
        findAvailableTime(fromDate, hours, meetingsPerDay, holidays);
    }
}
