package basic_commands;

import interfaces.Command;
import main_classes.Calendar;
import main_classes.GlobalMethods;

import java.io.File;

public class Save implements Command {
    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            System.out.println("Error: Requires no arguments.\n");
            return;
        }

        if (!new File("calendars\\" + Calendar.get().getName()).exists()) {
            System.out.println("Calendar \"" + Calendar.get().getName() + "\" does not exist.\n");
            return;
        }

        if (GlobalMethods.saveCalendar("calendars\\" + Calendar.get().getName()))
            System.out.println("Calendar successfully saved.\n");
    }
}
