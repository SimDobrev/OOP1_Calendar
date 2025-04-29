package commands;

import parsers.DateParser;
import main_classes.Calendar;
import main_classes.CalendarList;
import interfaces.Command;

import java.sql.Date;

public class Holiday implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 3) {
            System.out.println(args.length > 3 ?
                    "Error: Too many arguments." :
                    "Error: Not enough arguments.");
            System.out.println("Example: \"Input> holiday <calendar_name> <date>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        Date date = new DateParser().parse(args[2]);
        if (date == null) return;

        if (!calendar.getHolidays().contains(date))
            calendar.addHoliday(date);
        else System.out.println("Date is already set as a holiday!");
    }
}
