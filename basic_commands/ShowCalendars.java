package basic_commands;

import interfaces.Command;

import java.io.File;

public class ShowCalendars implements Command {
    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            System.out.println("Error: Too many arguments.");
            System.out.println("Example: \"Input> show_all\" or \"Input> showAll\"\n");
            return;
        }

        File[] directories = new File("calendars").listFiles(File::isDirectory);
        if (directories == null) {
            System.out.println("No calendars found.\n");
            return;
        }

        for (File directory : directories)
            System.out.println("- " + directory.getName());
        System.out.println();
    }
}
