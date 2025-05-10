package commands;

import main_classes.Calendar;
import main_classes.GlobalMethods;
import interfaces.Command;
import parsers.DateParser;

import java.sql.Date;

public class FindSlot implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 3) {
            System.out.println(args.length > 3 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"findsLot <from_date> <hours>\"\n");
            return;
        }

        Date fromDate = new DateParser().parse(args[1]);
        if (fromDate == null) return;

        int hours;
        try {
            hours = Integer.parseInt(args[2]);
            if (hours < 1 || hours > 9) {
                System.out.println("Error: Minimum 1 hour, maximum 9 hours.\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entered value is not an integer.\n");
            return;
        }

        GlobalMethods.findAvailableTime(fromDate, hours, GlobalMethods.getBusyDays(Calendar.get()));
    }
}
