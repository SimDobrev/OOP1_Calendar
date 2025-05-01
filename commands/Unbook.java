package commands;

import parsers.DateParser;
import parsers.TimeParser;
import main_classes.Calendar;
import interfaces.Command;

import java.sql.Time;
import java.sql.Date;

public class Unbook implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 4) {
            System.out.println(args.length > 4 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"unbook <date> <start_time> <end_time>\"\n");
            return;
        }

        Calendar calendar = Calendar.get();

        if (calendar.getMeetings().isEmpty()) {
            System.out.println("Error: Calendar has no booked meetings.\n");
            return;
        }

        Date date = new DateParser().parse(args[1]);
        Time startTime = new TimeParser().parse(args[2]);
        Time endTime = new TimeParser().parse(args[3]);
        if (date == null || startTime == null || endTime == null) return;
        if (calendar.isHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.\n");
            return;
        }

        if (startTime.compareTo(endTime) >= 0) {
            System.out.println("Error: Starting time cannot be greater than or equal to the end time.\n");
            return;
        }

        if (!calendar.getMeetings().removeIf(
                meeting -> meeting.getDate().equals(date) &&
                meeting.getStartTime().equals(startTime) &&
                meeting.getEndTime().equals(endTime))) {
            System.out.println("Error: Meeting not found.\n");
            return;
        }

        Calendar.set(calendar);
        System.out.println("Meeting successfully unbooked.\n");
    }
}
