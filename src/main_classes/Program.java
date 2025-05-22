package main_classes;

import interfaces.Command;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * The {@code Program} class is where the user is prompted to enter a command
 * with its required arguments, after which the program proceeds to execute the
 * command, if it is a valid command.
 */
public class Program {
    /**
     * The currently open calendar.
     */
    private static Calendar mainCalendar = null;
    /**
     * A map with all available commands and keywords for accessing.
     */
    private final static Map<String, Command> commands = Commands.load();
    /**
     * A set with commands that are allowed before opening a calendar.
     */
    private final static Set<String> allowedStartingCommands = getAllowedStartingCommands();

    /**
     * Gets the user input and executes the chosen command, only if the first word is an
     * available command, otherwise prints an appropriate message.
     */
    public static void start() {
        new File("..\\calendars").mkdir();

        while (true) {
            System.out.print("Input> ");
            String[] userInput = new Scanner(System.in).nextLine().split(" ");

            if (userInput[0].equalsIgnoreCase("exit")) break;
            if (mainCalendar == null && !allowedStartingCommands.contains(userInput[0].toLowerCase())) {
                System.out.println("Error: " +
                        (commands.containsKey(userInput[0].toLowerCase())
                        ? "No calendar open."
                        : "\"" + userInput[0] + "\" is not an available command."));
                continue;
            }

            try {
                commands.get(userInput[0].toLowerCase()).execute(getArguments(userInput));
            } catch (RuntimeException e) {
                System.out.println("Error: \"" + userInput[0] + "\" is not an available command.");
            }
            System.out.println();
        }

        System.out.println("Exiting the program...");
        System.exit(0);
    }

    /**
     * Gets the user input and returns an array with the arguments.
     * @param userInput The user input.
     * @return An array with the given arguments.
     */
    private static String[] getArguments(String[] userInput) {
        String[] arguments = new String[userInput.length - 1];
        System.arraycopy(userInput, 1, arguments, 0, userInput.length - 1);
        return arguments;
    }

    /**
     * Gets and returns the main calendar.
     * @return The main calendar.
     */
    public static Calendar getMainCalendar() {
        return mainCalendar;
    }

    /**
     * Sets the main calendar to the given instance.
     * @param calendar The given instance.
     */
    public static void setMainCalendar(Calendar calendar) {
        Program.mainCalendar = calendar;
    }

    /**
     * Closes the main calendar by setting it to {@code null}.
     */
    public static void closeMainCalendar() {
        Program.mainCalendar = null;
    }

    /**
     * Loads the main calendar's meetings and holidays.
     * @param calendarName The name of the calendar.
     */
    public static void loadMainCalendar(String calendarName) {
        mainCalendar = CalendarLoader.loadCalendar(calendarName);
        System.out.println(mainCalendar == null
                ? "Calendar could not be opened."
                : "Calendar successfully opened.");
    }

    /**
     * Gets and returns a {@code Set<String>} with all commands, allowed before
     * opening a calendar.
     * @return A {@code Set<String>} with the commands, allowed before
     * opening a calendar.
     */
    private static Set<String> getAllowedStartingCommands() {
        Set<String> allowedStartingCommands = new HashSet<>();
        allowedStartingCommands.add("open");
        allowedStartingCommands.add("help");
        allowedStartingCommands.add("showcalendars");
        allowedStartingCommands.add("create");
        allowedStartingCommands.add("delete");
        return allowedStartingCommands;
    }
}
