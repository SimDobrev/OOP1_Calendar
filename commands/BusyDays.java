package commands;

import parsers.DateParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;

import java.sql.Date;
import java.util.*;

public class BusyDays implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 3) {
            System.out.println(args.length > 3 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"busyDays <date_from> <date_to>\"\n");
            return;
        }

        Date dateFrom = new DateParser().parse(args[1]);
        Date dateTo = new DateParser().parse(args[2]);
        if (dateFrom == null || dateTo == null) return;

        if (dateFrom.compareTo(dateTo) < 0)
            listBusyDays(Calendar.get(), dateFrom, dateTo);
        else System.out.println("Error: Starting date cannot be later than or equal to the final date.\n");
    }

    private void listBusyDays(Calendar calendar, Date date_from, Date date_to) {
        List<Meeting> meetingsInRange = new ArrayList<>(calendar.getMeetings());
        meetingsInRange.removeIf(meeting -> meeting.getDate().compareTo(date_from) < 0 ||
                        meeting.getDate().compareTo(date_to) > 0);

        Map<Date, Integer> meetingsPerDay = getMeetingsPerDay(meetingsInRange);
        if (meetingsPerDay.isEmpty()) {
            System.out.println("No busy days found.\n");
            return;
        }

        List<Map.Entry<Date, Integer>> entryList = new ArrayList<>(meetingsPerDay.entrySet());
        entryList.sort(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Date, Integer> entry : entryList)
            System.out.println(entry.getKey() + ": " + entry.getValue() + " meetings");
        System.out.println();
    }

    private Map<Date, Integer> getMeetingsPerDay(List<Meeting> meetings) {
        Map<Date, Integer> meetingsPerDay = new HashMap<>();

        Meeting previous = null;
        int count = 1;
        for (Meeting meeting : meetings) {
            if (previous == null) {
                previous = meeting;
                continue;
            }
            if (meeting.getDate() == previous.getDate()) count++;
            else {
                meetingsPerDay.put(previous.getDate(), count);
                previous = meeting;
                count = 1;
            }
        }

        return meetingsPerDay;
    }
}
