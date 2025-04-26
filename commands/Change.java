package commands;

import parsers.DateParser;
import parsers.TimeParser;
import main_classes.Calendar;
import main_classes.CalendarList;
import main_classes.Meeting;
import interfaces.Command;

import java.sql.Time;
import java.sql.Date;

public class Change implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 6) {
            System.out.println("Input: \"book <calendar_name> <date> <start_time> <option> <new_value>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        Date date = new DateParser().parse(args[2]);
        Time startTime = new TimeParser().parse(args[3]);
        if (date == null || startTime == null) return;

        setNewValue(calendar, date, startTime, args[4], args[5]);
    }

    private void setNewValue(Calendar calendar, Date date, Time startTime, String option, String newValue) {
        for (Meeting meeting : calendar.getMeetings()) {
            if (meeting.getDate().equals(date) && meeting.getStartTime().equals(startTime)) {
                switch (option) {
                    case "date": {
                        Date newDate = new DateParser().parse(newValue);
                        if (newDate == null) return;
                        else meeting.setDate(newDate);
                        break;
                    }
                    case "startTime": {
                        Time newStartTime = new TimeParser().parse(newValue);
                        if (newStartTime == null) return;
                        meeting.setStartTime(newStartTime);
                        break;
                    }
                    case "endTime": {
                        Time newEndTime = new TimeParser().parse(newValue);
                        if (newEndTime == null) return;
                        meeting.setEndTime(newEndTime);
                        break;
                    }
                    case "name":
                        meeting.setName(newValue);
                        break;
                    case "note":
                        meeting.setNote(newValue);
                        break;
                    default:
                        System.out.println("Incorrect option!\n");
                        break;
                }
            }
        }
    }
}
