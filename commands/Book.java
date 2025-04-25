package commands;

import parsers.TimeParser;
import main_classes.Calendar;
import main_classes.CalendarList;
import main_classes.Meeting;
import interfaces.Command;
import parsers.DateParser;

import java.sql.Time;
import java.sql.Date;

public class Book implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 7) {
            System.out.println("Input: \"book <calendar_name> <date> <start_time> <end_time> <name> <note>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        Date date = new DateParser().parse(args[2]);
        Time startTime = new TimeParser().parse(args[3]);
        Time endTime = new TimeParser().parse(args[4]);

        if (date == null || startTime == null || endTime == null) return;

        if (startTime.compareTo(endTime) >= 0) {
            System.out.println("Starting time cannot be later than or equal to the end time!\n");
            return;
        }

        Meeting meeting = new Meeting(date, startTime, endTime, args[5], args[6]);
        if (!calendar.getMeetings().contains(meeting))
            calendar.addMeeting(meeting);
        else System.out.println("Meeting has already been set!\n");
    }
}
