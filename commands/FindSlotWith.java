package commands;

import interfaces.Command;
import main_classes.Calendar;
import main_classes.GlobalMethods;
import parsers.DateParser;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class FindSlotWith implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 4) {
            System.out.println(args.length > 4 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"findsLot <from_date> <hours> <calendar_name>\"\n");
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

        if (args[3].equals(Calendar.get().getName())) {
            System.out.println("Error: Tried opening an already opened calendar.\n");
            return;
        }

        Calendar secondCalendar = GlobalMethods.loadCalendar(args[3]);
        if (secondCalendar == null) return;

        Map<Date, Map<Time, Time>> busyDays = combineBusyDays(
                GlobalMethods.getBusyDays(Calendar.get()),
                GlobalMethods.getBusyDays(secondCalendar)
        );
        GlobalMethods.findAvailableTime(fromDate, hours, busyDays);
    }

    private Map<Date, Map<Time, Time>> combineBusyDays(Map<Date, Map<Time, Time>> busyDays1, Map<Date, Map<Time, Time>> busyDays2) {
        Map<Date, Map<Time, Time>> combinedBusyDays = new HashMap<>(busyDays1);
        for (Date date : busyDays2.keySet()) {
            if (busyDays1.containsKey(date))
                combinedBusyDays.put(date, new HashMap<>());
            for (Map.Entry<Time, Time> entry : busyDays2.get(date).entrySet())
                combinedBusyDays.get(date).put(entry.getKey(), entry.getValue());
        }
        return combinedBusyDays;
    }
}
