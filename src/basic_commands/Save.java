package basic_commands;

import abstract_classes.SaveCommand;
import main_classes.Program;

import java.io.File;

/**
 * The {@code Save} class is a command, which saves any changes to the currently
 * set main calendar.
 */
public class Save extends SaveCommand {
    /**
     * Saves any changes to the currently set main calendar.
     * <p>
     *     Checks whether the main calendar's files exist. If {@code true},
     *     the method {@code saveCalendar} from the parent class saves all
     *     changes to the calendar from the current session. If {@code false},
     *     the necessary files are created and the method {@code saveCalendar}
     *     saves the calendar on them.
     * </p>
     */
    @Override
    public void execute(String... args) {
        if (args.length > 0) {
            System.out.println("Error: Requires no arguments.");
            return;
        }

        new File("calendars").mkdir();

        if (!new File("calendars\\" + Program.getMainCalendar().getName()).exists())
            new Create().execute(Program.getMainCalendar().getName());

        if (saveCalendar(Program.getMainCalendar().getName()))
            System.out.println("Calendar successfully saved.");
        else System.out.println("Error: Calendar could not be saved properly.");
    }
}
