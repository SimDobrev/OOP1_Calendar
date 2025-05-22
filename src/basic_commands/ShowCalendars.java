package basic_commands;

import interfaces.Command;

import java.io.File;

/**
 * The {@code ShowCalendars} class is a command, which prints a list of all
 * existing calendars.
 */
public class ShowCalendars implements Command {
    /**
     * Prints a list with the existing calendars.
     * <p>
     *     Checks whether there are any existing calendars or not.
     *     If there are, the method prints a list of them.
     *     If not, it prints an appropriate error message.
     * </p>
     */
    @Override
    public void execute(String... args) {
        if (args.length > 0) {
            System.out.println("Error: Requires no arguments.");
            return;
        }

        if (!new File("..\\calendars").exists()) {
            System.out.println("No calendars found.");
            return;
        }

        File[] directories = new File("..\\calendars").listFiles(File::isDirectory);
        if (directories == null || directories.length == 0) {
            System.out.println("No calendars found.");
            return;
        }

        System.out.println("\nAvailable calendars:");
        for (File directory : directories)
            System.out.println("- " + directory.getName());
    }
}
