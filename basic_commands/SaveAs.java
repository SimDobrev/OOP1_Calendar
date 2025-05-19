package basic_commands;

import abstract_classes.SaveCommand;

import java.io.File;
import java.util.Scanner;

/**
 * The {@code SaveAs} class is a command, which either saves the currently set main
 * calendar onto newly created files, or overwrites an already existing calendar.
 */
public class SaveAs extends SaveCommand {
    /**
     * Either saves the currently set main calendar onto newly created files,
     * or overwrites an already existing calendar.
     * <p>
     *     Checks whether the given calendar name is the name of an already existing
     *     calendar. If {@code false}, the method creates the necessary files and
     *     saves the calendar under the new name. If {@code true}, the method prompts
     *     the user to answer whether they want to overwrite the already existing
     *     calendar. If they agree, the old calendar is replaced, otherwise the
     *     operation is canceled.
     * </p>
     * @param args Requires only a new calendar name.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"saveAs <new_calendar_name>\"");
            return;
        }

        new File("calendars").mkdir();

        if (new File("calendars\\" + args[0]).exists()) {
            System.out.println("Calendar \"" + args[0] + "\" already exists.");
            System.out.println("Do you wish to overwrite it?");

            String userInput = getUserInput();
            if (userInput.equals("yes") || userInput.equals("y"))
                System.out.println(saveCalendar(args[0])
                        ? "Calendar successfully saved as \"" + args[0] + "\""
                        : "Error: Calendar could not be saved properly.");
            else System.out.println("Operation cancelled.");
        }
        else {
            new Create().execute(args[0]);
            System.out.println(saveCalendar(args[0])
                    ? "Calendar successfully saved as \"" + args[0] + "\""
                    : "Error: Calendar could not be saved properly.");
        }
    }

    /**
     * Gets and returns the user's choice.
     * <p>
     *     The user will be continuously prompted, until they enter {@code yes} or
     *     {@code no}. After entering one or the other, the user's choice will be accepted and
     *     returned to the caller method.
     * </p>
     * @return The user's choice.
     */
    private String getUserInput() {
        while (true) {
            System.out.print("Input> ");
            String userInput = new Scanner(System.in).nextLine().toLowerCase();
            switch (userInput) {
                case "yes": case "y":
                case "no": case "n":
                    return userInput;
                default:
                    System.out.println("Error: Please enter \"Yes\" or \"No\". (or just \"Y\" or \"N\")");
                    break;
            }
        }
    }
}
