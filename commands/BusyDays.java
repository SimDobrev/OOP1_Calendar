package commands;

import parsers.DateParser;
import main_classes.Calendar;
import main_classes.CalendarList;
import main_classes.Meeting;
import interfaces.Command;

import java.util.*;

public class BusyDays implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 4) {
            System.out.println(args.length > 4 ?
                    "Error: Too many arguments." :
                    "Error: Not enough arguments.");
            System.out.println("Example: \"Input> book <calendar_name> <date_from> <date_to>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        Date dateFrom = new DateParser().parse(args[2]);
        Date dateTo = new DateParser().parse(args[3]);
        if (dateFrom == null || dateTo == null) return;

        if (dateFrom.compareTo(dateTo) < 0)
            getBusyDays(calendar, dateFrom, dateTo);
    }

    private void getBusyDays(Calendar calendar, Date date_from, Date date_to) {
        Set<Meeting> busyDays = new HashSet<>();
        for (Meeting meeting : calendar.getMeetings()) {
            if (meeting.getDate().compareTo(date_from) < 1) continue;
            if (meeting.getDate().compareTo(date_to) > 0) break;
            busyDays.add(meeting);
        }

        for (Meeting meeting : busyDays)
            System.out.println(meeting);
    }
}
