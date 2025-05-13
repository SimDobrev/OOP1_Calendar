package commands;

import interfaces.Command;
import main_classes.Calendar;
import main_classes.Program;
import parsers.DateParser;

import java.sql.Date;

public class UnsetHoliday implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"holiday <date>\"\n");
            return;
        }

        Calendar calendar = Program.getMainCalendar();

        Date date = new DateParser().parse(args[0]);
        if (date == null) return;

        if (calendar.getHolidays().isEmpty()) {
            System.out.println("Error: No holidays set.\n");
            return;
        }

        if (calendar.containsHoliday(date)) {
            calendar.removeHoliday(date);
            Program.setMainCalendar(calendar);
            System.out.println("Holiday successfully unset.\n");
        }
        else System.out.println("Error: Given date is not set as a holiday.\n");
    }
}
