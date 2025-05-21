package commands;

import main_classes.Calendar;
import main_classes.Program;
import parsers.LocalDateParser;
import interfaces.Command;

import java.time.LocalDate;

/**
 * The {@code Holiday} class is a command, which sets a given date of type
 * {@code LocalDate} as a holiday.
 */
public class Holiday implements Command {
    /**
     * Sets a given date as a holiday.
     * <p>
     *     Checks whether the given date is already set as a holiday. If not
     *     the method sets it by adding it to the main calendar's
     *     {@code Set<LocalDate>} of holidays, otherwise, it prints an appropriate
     *     error message.
     * </p>
     * @param args Requires only a date.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"holiday <date>\"");
            return;
        }

        Calendar calendar = Program.getMainCalendar();

        LocalDate date = new LocalDateParser().parse(args[0]);
        if (date == null) return;

        if (!calendar.containsHoliday(date)) {
            calendar.addHoliday(date);
            calendar.sortHolidays();
            Program.setMainCalendar(calendar);
            System.out.println("New holiday successfully set.");
        }
        else System.out.println("Error: Given date is already set as a holiday.");
    }
}
