package basic_commands;

import interfaces.Command;
import main_classes.Calendar;
import main_classes.GlobalMethods;

import java.io.File;
import java.util.Scanner;

public class SaveAs implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"saveAs <file_name>\"\n");
            return;
        }

        if (new File("calendars\\" + Calendar.get().getName()).exists()) {
            System.out.println("Calendar \"" + Calendar.get().getName() + "\" already exists.");
            System.out.println("Do you wish to overwrite it?");

            if (getUserInput().equals("yes") || getUserInput().equals("y")) {
                if (GlobalMethods.saveCalendar("calendars\\" + args[1]))
                    System.out.println("Calendar successfully saved as \"" + args[1] + "\"\n");
            }
            else System.out.println("Operation cancelled.\n");
        }
    }

    private String getUserInput() {
        while (true) {
            String userInput = new Scanner(System.in).toString().toLowerCase();
            switch (userInput) {
                case "yes": case "y":
                case "no": case "n":
                    return userInput;
                default:
                    System.out.println("Error: Please enter \"Yes\" or \"No\".\n");
                    break;
            }
        }
    }
}
