package basic_commands;

import interfaces.Command;
import main_classes.Calendar;
import main_classes.GlobalMethods;

import java.io.File;

public class Open implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"open <calendar_name>\"\n");
            return;
        }

        if (Calendar.get() != null) {
            System.out.println("Error: A calendar is already opened.\n");
            return;
        }

        File file = new File("calendars\\" + args[1]);
        if (!file.exists()) GlobalMethods.createCalendar(args[1]);
        Calendar.set(GlobalMethods.loadCalendar(args[1]));
    }
}
