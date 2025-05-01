package commands;

import parsers.DateParser;
import main_classes.Calendar;
import interfaces.Command;

import java.sql.Date;

public class Holiday implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"holiday <date>\"\n");
            return;
        }

        Calendar calendar = Calendar.get();

        Date date = new DateParser().parse(args[1]);
        if (date == null) return;

        if (calendar.getHolidays().isEmpty()) {
            System.out.println("Error: No holidays set.\n");
            return;
        }

        if (!calendar.getHolidays().contains(date)) {
            calendar.addHoliday(date);
            Calendar.set(calendar);
            System.out.println("New holiday successfully set.\n");
        }
        else System.out.println("Error: Date is already set as a holiday.\n");
    }
}
