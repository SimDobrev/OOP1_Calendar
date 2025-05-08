package basic_commands;

import interfaces.Command;

import java.util.ArrayList;
import java.util.List;

public class Help implements Command {
    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            System.out.println("Error: Requires no arguments.\n");
            return;
        }
        showMenu();
    }

    private void showMenu() {
        System.out.println("\nAvailable commands:");
        List<String> menu = getMenu();
        for (String command : menu) {
            System.out.println("- " + command);
            if (command.startsWith("exit"))
                System.out.println();
        }
        System.out.println();
    }

    private List<String> getMenu() {
        List<String> commands = new ArrayList<>();
        commands.add("open              Opens a chosen calendar");
        commands.add("save              Saves the contents of the opened calendar");
        commands.add("saveAs            Saves the contents of the opened calendar to a new file");
        commands.add("close             Closes the current calendar");
        commands.add("help              Shows a menu with the available commands");
        commands.add("showCalendars     Shows a menu with the existing calendars");
        commands.add("create            Creates a new calendar");
        commands.add("delete            Deletes a selected calendar");
        commands.add("exit              Exits the program");
        commands.add("book              Books a meeting");
        commands.add("unbook            Unbooks a meeting");
        commands.add("agenda            Shows a list of all meetings on a given date");
        commands.add("change            Updates a given value within a meeting");
        commands.add("find              Shows a list of meetings that match a given name or note");
        commands.add("holiday           Sets a given date as a holiday");
        commands.add("busyDays          Shows a list of all days with meetings in order of most to least meetings");
        commands.add("findsLot          Finds an appropriate date and time for a meeting");
        commands.add("findsLotWith      Finds an appropriate date and time for a meeting (Supports multiple calendars)");
        commands.add("merge             Merges the given calendars into the current one");
        return commands;
    }
}
