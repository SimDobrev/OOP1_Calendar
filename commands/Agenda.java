package commands;

import parsers.DateParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Agenda implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"agenda <date>\"\n");
            return;
        }

        Date date = new DateParser().parse(args[1]);
        if (date == null) return;
        if (Calendar.get().isHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.\n");
            return;
        }

        List<Meeting> agenda = getAgenda(date);
        if (!agenda.isEmpty())
            for (Meeting meeting : agenda)
                System.out.println(meeting);
        else System.out.println("Error: No meetings set on this day.\n");
    }

    private List<Meeting> getAgenda(Date date) {
        List<Meeting> found = new ArrayList<>();
        for (Meeting meeting : Calendar.get().getMeetings())
            if (date.equals(meeting.getDate()))
                found.add(meeting);
        return found;
    }
}
