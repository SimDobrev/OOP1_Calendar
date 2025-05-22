package basic_commands;

import interfaces.Command;

import java.io.File;

/**
 * The {@code Delete} class is a command, which deletes a calendar with the
 * given name, only if it exists.
 */
public class Delete implements Command {
    /**
     * Deletes a calendar with the given name, only if it exists.
     * @param args Requires only the calendar name.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"delete <calendar_name>\"");
            return;
        }
        deleteCalendar(args[0]);
    }

    /**
     * Deletes the calendar with the given name.
     * <p>
     *     If the calendar's directory and files exist, the program deletes it and
     *     all of its files. If not the method prints an appropriate error message.
     * </p>
     * @param calendarName The name of the calendar.
     */
    private void deleteCalendar(String calendarName) {
        try {
            if (!new File("..\\calendars").exists()) {
                System.out.println("Error: No calendars found.");
                return;
            }

            if (!new File("..\\calendars\\" + calendarName).exists()) {
                System.out.println("Error: Calendar \"" + calendarName + "\"not found.");
                return;
            }

            new File("..\\calendars\\" + calendarName + "\\meetings.txt").delete();
            new File("..\\calendars\\" + calendarName + "\\holidays.txt").delete();
            new File("..\\calendars\\" + calendarName).delete();
        } catch (RuntimeException e) {
            System.out.println("Error: Calendar could not be deleted.");
            return;
        }
        System.out.println("Calendar successfully deleted.");
    }
}
