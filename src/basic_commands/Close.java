package basic_commands;

import interfaces.Command;
import main_classes.Program;

import java.util.Scanner;

/**
 * The {@code Close} class is a command, which closes the currently set main
 * calendar.
 * <p>
 *      The user will be prompted with the question whether they would like to save the
 *      calendar before closing it. If the user chooses to save it, the calendar
 *      will be saved and then closed. Otherwise, the calendar will be closed and all
 *      changes will be lost.
 * </p>
 */
public class Close implements Command {
    /**
     * Closes the currently set main calendar.
     * <p>
     *     Checks whether the main calendar is set. If it is set as {@code null} the
     *     method returns an appropriate error message, otherwise, it closes the
     *     calendar by setting it to {@code null}.
     * </p>
     */
    @Override
    public void execute(String... args) {
        if (args.length > 0) {
            System.out.println("Error: Requires no arguments.");
            return;
        }

        if (Program.getMainCalendar() == null) {
            System.out.println("Error: No calendar opened.");
            return;
        }

        System.out.println("Do you wish to save the calendar before closing?");
        String userInput = getUserInput();
        if (userInput.equals("yes") || userInput.equals("y"))
            new Save().execute();

        System.out.println("Calendar \"" + Program.getMainCalendar().getName() + "\" successfully closed.");
        Program.closeMainCalendar();
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
