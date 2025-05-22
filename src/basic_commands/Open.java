package basic_commands;

import interfaces.Command;
import main_classes.Program;

import java.io.File;

/**
 * The {@code Open} class is a command, which opens a calendar with the given name
 * and sets it as the main calendar for the program to use.
 */
public class Open implements Command {
    /**
     * Opens the calendar with the given name.
     * <p>
     *     Checks whether a calendar has already been set as the main calendar. If not
     *     it opens the calendar with the given name's file and reads it, if it exists,
     *     sets it as the main calendar and closes the file. Otherwise, it prints an
     *     appropriate error message.
     * </p>
     * @param args Requires only the calendar name.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"open <calendar_name>\"");
            return;
        }

        if (Program.getMainCalendar() != null) {
            System.out.println("Error: A calendar is already opened.");
            return;
        }

        new File("..\\calendars").mkdir();

        File file = new File("..\\calendars\\" + args[0]);
        if (!file.exists())
            new Create().execute(args[0]);
        Program.loadMainCalendar(args[0]);
    }
}
