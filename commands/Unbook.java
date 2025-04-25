package commands;

import parsers.DateParser;
import parsers.TimeParser;
import main_classes.Calendar;
import main_classes.CalendarList;
import interfaces.Command;

import java.sql.Time;
import java.sql.Date;

public class Unbook implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 5) {
            System.out.println("Input: \"unbook <calendar_name> <date> <start_time> <end_time>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        Date date = new DateParser().parse(args[2]);
        Time startTime = new TimeParser().parse(args[3]);
        Time endTime = new TimeParser().parse(args[4]);

        if (date == null || startTime == null || endTime == null) return;

        if (startTime.compareTo(endTime) >= 0) {
            System.out.println("Starting time cannot be greater than or equal to the end time!\n");
            return;
        }

        calendar.getMeetings().removeIf(
                meeting -> meeting.getDate().equals(date) &&
                meeting.getStartTime().equals(startTime) &&
                meeting.getEndTime().equals(endTime));
    }
}
