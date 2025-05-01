package commands;

import parsers.DateParser;
import parsers.TimeParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;

import java.sql.Time;
import java.sql.Date;

public class Change implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 5) {
            System.out.println(args.length > 5 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"change <date> <start_time> <option> <new_value>\"\n");
            return;
        }

        Calendar calendar = Calendar.get();

        Date date = new DateParser().parse(args[1]);
        Time startTime = new TimeParser().parse(args[2]);
        if (date == null || startTime == null) return;

        if (calendar.isHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.\n");
            return;
        }

        calendar = setNewValue(calendar, date, startTime, args[3], args[4]);
        if (calendar != null) {
            Calendar.set(calendar);
            System.out.println("Value successfully changed.\n");
        }
    }

    private Calendar setNewValue(Calendar calendar, Date date, Time startTime, String option, String newValue) {
        for (Meeting meeting : calendar.getMeetings()) {
            if (meeting.getDate().equals(date) && meeting.getStartTime().equals(startTime)) {
                switch (option.toLowerCase()) {
                    case "date": {
                        Date newDate = new DateParser().parse(newValue);
                        if (newDate == null) return null;
                        else meeting.setDate(newDate);
                        break;
                    }
                    case "start_time":
                    case "starttime": {
                        Time newStartTime = new TimeParser().parse(newValue);
                        if (newStartTime == null) return null;
                        meeting.setStartTime(newStartTime);
                        break;
                    }
                    case "end_time":
                    case "endtime": {
                        Time newEndTime = new TimeParser().parse(newValue);
                        if (newEndTime == null) return null;
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
                        System.out.println("Error: Incorrect option.");
                        System.out.println("Available options are: date, start_time, end_time, name, note.\n");
                        break;
                }
            }
        }
        return calendar;
    }
}
