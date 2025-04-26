package commands;

import parsers.DateParser;
import main_classes.Calendar;
import main_classes.CalendarList;
import main_classes.Meeting;
import interfaces.Command;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Agenda implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 7) {
            System.out.println("Input: \"book <calendar_name> <date>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        Date date = new DateParser().parse(args[2]);
        if (date == null) return;

        if (isHoliday(calendar, date)) {
            System.out.println("This day is set as a holiday!");
            return;
        }

        List<Meeting> agenda = getAgenda(calendar, date);
        if (!agenda.isEmpty())
            for (Meeting meeting : agenda)
                System.out.println(meeting);
        else System.out.println("No meetings set on this day!");
    }

    private boolean isHoliday(Calendar calendar, Date date) {
        return calendar.getHolidays().contains(date);
    }

    private List<Meeting> getAgenda(Calendar calendar, Date date) {
        List<Meeting> found = new ArrayList<>();
        for (Meeting meeting : calendar.getMeetings())
            if (date.equals(meeting.getDate()))
                found.add(meeting);
        return found;
    }
}
