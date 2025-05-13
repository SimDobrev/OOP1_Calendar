package basic_commands;

import interfaces.Command;

import java.io.File;

public class Delete implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"delete <calendar_name>\"\n");
            return;
        }

        if (!new File("calendars\\" + args[0]).exists())
            System.out.println("Error: Calendar \"" + args[0] + "\" does not exist.\n");
        else deleteCalendar(args[0]);
    }

    private void deleteCalendar(String calendarName) {
        try {
            if (!new File("calendars").exists()) {
                System.out.println("Error: No calendars found.\n");
                return;
            }

            if (!new File("calendars" + calendarName).exists()) {
                System.out.println("Error: Calendar \"" + calendarName + "\"not found.\n");
                return;
            }

            new File("calendars\\" + calendarName + "\\meetings.txt").delete();
            new File("calendars\\" + calendarName + "\\holidays.txt").delete();
            new File("calendars\\" + calendarName).delete();
        } catch (RuntimeException e) {
            System.out.println("Error: Calendar could not be deleted.\n");
            return;
        }
        System.out.println("Calendar successfully deleted.\n");
    }
}
