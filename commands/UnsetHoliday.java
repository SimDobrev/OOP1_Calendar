package commands;

import interfaces.Command;
import main_classes.Calendar;
import main_classes.Program;
import parsers.LocalDateParser;

import java.time.LocalDate;

/**
 * The {@code UnsetHoliday} class is a command, which unsets a given holiday.
 */
public class UnsetHoliday implements Command {
    /**
     * Unsets a given holiday date, if it is already set.
     * <p>
     *     Checks whether the given date is contained within the holidays
     *     {@code Set<LocalDate>}. If true, the method unsets the holiday,
     *     otherwise, it prints an appropriate error message.
     * </p>
     * @param args Requires only a date.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"unsetHoliday <date>\"");
            return;
        }

        Calendar calendar = Program.getMainCalendar();

        LocalDate date = new LocalDateParser().parse(args[0]);
        if (date == null) return;

        if (calendar.getHolidays().isEmpty()) {
            System.out.println("Error: No holidays set.");
            return;
        }

        if (calendar.containsHoliday(date)) {
            calendar.removeHoliday(date);
            Program.setMainCalendar(calendar);
            System.out.println("Holiday successfully unset.");
        }
        else System.out.println("Error: Given date is not set as a holiday.");
    }
}
