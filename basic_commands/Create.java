package basic_commands;

import interfaces.Command;

import java.io.File;
import java.io.IOException;

public class Create implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"create <calendar_name>\"\n");
            return;
        }
        createCalendar(args[0]);
    }

    private void createCalendar(String calendarName) {
        try {
            if (new File("calendars" + calendarName).exists()) {
                System.out.println("Error: Calendar \"" + calendarName + "\" already exists.\n");
                return;
            }
            new File("calendars").mkdir();
            new File("calendars\\" + calendarName).mkdir();
            new File("calendars\\" + calendarName + "\\meetings.txt").createNewFile();
            new File("calendars\\" + calendarName + "\\holidays.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("Error: Calendar could not be created.\n");
            return;
        }
        System.out.println("Calendar successfully created.\n");
    }
}
