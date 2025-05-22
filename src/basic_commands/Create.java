package basic_commands;

import interfaces.Command;

import java.io.File;
import java.io.IOException;

/**
 * The {@code Create} class is a command, which creates a new calendar with the
 * given name, only if it does not already exist.
 */
public class Create implements Command {
    /**
     * Creates a new calendar with the given name.
     * <p>
     *      Checks whether a calendar with the given name already exists and if not the
     *      method creates it with all of its necessary files.
     * </p>
     * @param args Requires only the new calendar name.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"create <calendar_name>\"");
            return;
        }
        createCalendar(args[0]);
    }

    /**
     * Creates the new calendar.
     * <p>
     *      Checks whether a calendar with the given name already exists. If not, the
     *      program creates a new directory for the calendar with all necessary files,
     *      otherwise it prints an appropriate error message.
     * </p>
     * @param calendarName The name of the new calendar.
     */
    private void createCalendar(String calendarName) {
        try {
            if (new File("..\\calendars" + calendarName).exists()) {
                System.out.println("Error: Calendar \"" + calendarName + "\" already exists.");
                return;
            }
            new File("..\\calendars").mkdir();
            new File("..\\calendars\\" + calendarName).mkdir();
            new File("..\\calendars\\" + calendarName + "\\meetings.txt").createNewFile();
            new File("..\\calendars\\" + calendarName + "\\holidays.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("Error: Calendar could not be created.");
            return;
        }
        System.out.println("Calendar successfully created.");
    }
}
