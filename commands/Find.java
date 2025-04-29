package commands;

import main_classes.Calendar;
import main_classes.CalendarList;
import main_classes.Meeting;
import interfaces.Command;

public class Find implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 3) {
            System.out.println(args.length > 3 ?
                    "Error: Too many arguments." :
                    "Error: Not enough arguments.");
            System.out.println("Example: \"Input> find <calendar_name> <value>\"\n");
            return;
        }

        Calendar calendar = CalendarList.findCalendar(args[1]);
        if (calendar == null) return;

        findMeetings(calendar, args[2]);
    }

    private void findMeetings(Calendar calendar, String value) {
        for (Meeting meeting : calendar.getMeetings())
            if (meeting.getName().equals(value) || meeting.getNote().equals(value))
                System.out.println(meeting);
    }
}
