package basic_commands;

import interfaces.Command;
import main_classes.Calendar;

public class Close implements Command {
    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            System.out.println("Error: Requires no arguments.\n");
            return;
        }
        System.out.println("Calendar \"" + Calendar.get().getName() + "\" closed.\n");
        Calendar.set(null);
    }
}
