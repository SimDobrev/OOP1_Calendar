package commands;

import main_classes.GlobalMethods;
import parsers.TimeParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;
import parsers.DateParser;

import java.sql.Time;
import java.sql.Date;
import java.util.Map;

public class Book implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 6) {
            System.out.println(args.length > 6 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"book <date> <start_time> <end_time> <name> <note>\"\n");
            return;
        }

        Calendar calendar = Calendar.get();

        Date date = new DateParser().parse(args[1]);
        Time startTime = new TimeParser().parse(args[2]);
        Time endTime = new TimeParser().parse(args[3]);

        if (!canBookMeeting(date, startTime, endTime)) return;

        Meeting meeting = new Meeting(date, startTime, endTime, args[4], args[5]);
        if (!calendar.getMeetings().contains(meeting)) {
            calendar.addMeeting(meeting);
            Calendar.set(calendar);
            System.out.println("New meeting successfully booked.\n");
        }
        else System.out.println("Error: Meeting has already been booked.\n");
    }

    private boolean canBookMeeting(Date date, Time startTime, Time endTime) {
        if (date == null || startTime == null || endTime == null) return false;

        if (Calendar.get().isHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.\n");
            return false;
        }

        if (startTime.compareTo(endTime) >= 0) {
            System.out.println("Error: Starting time cannot be later than or equal to the end time.\n");
            return false;
        }

        if (!areValidTimes(date, startTime, endTime)) {
            System.out.println("Error: The given times do not fit within that day's schedule.\n");
            return false;
        }

        return true;
    }

    private boolean areValidTimes(Date date, Time startTime, Time endTime) {
        Map<Date, Map<Time, Time>> busyDays = GlobalMethods.getBusyDays(Calendar.get());
        if (!busyDays.containsKey(date)) return true;

        int index = 1;
        for (Map.Entry<Time, Time> meetingTimes : busyDays.get(date).entrySet()) {
            if (startTime.compareTo(meetingTimes.getKey()) < 0)
                return endTime.compareTo(meetingTimes.getKey()) <= 0;

            if (startTime.compareTo(meetingTimes.getValue()) >= 0) {
                if (index == busyDays.get(date).size()) return true;
                else index++;
            }
            else break;
        }
        return false;
    }
}
