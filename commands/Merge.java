package commands;

import main_classes.Calendar;
import main_classes.CalendarMethods;
import main_classes.Meeting;
import interfaces.Command;
import parsers.TimeParser;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class Merge implements Command {
    @Override
    public void execute(String... args) {
        if (args.length < 3) {
            System.out.println("Error: Missing arguments.\n" +
                    "Example: \"merge <calendar1_name> <calendar2_name>...\"\n");
            return;
        }

        Calendar[] calendars = new Calendar[args.length - 1];
        for (int i = 0; i < calendars.length; i++) {
            calendars[i] = CalendarMethods.loadCalendar(args[i + 1]);
            if (calendars[i] == null) return;
        }

        for (int i = 0; i < calendars.length; i++)
            CalendarMethods.deleteCalendar(args[i + 1]);

        CalendarMethods.createCalendar(Calendar.get().getName());
        Calendar.set(merge(calendars));
        System.out.println("Calendars merged.\nNOTE: Don't forget to save the new calendar!\n");
    }

    private Calendar merge(Calendar[] calendars) {
        Calendar newCalendar = Calendar.get();
        for (Calendar calendar : calendars) {
            for (Date holiday : calendar.getHolidays()) {
                if (newCalendar.getHolidays().contains(holiday)) continue;
                newCalendar.addHoliday(holiday);
            }

            for (Meeting newMeeting : calendar.getMeetings()) {
                if (newCalendar.getMeetings().contains(newMeeting)) continue;

                for (Meeting meeting : newCalendar.getMeetings())
                    if (newMeeting.getDate().equals(meeting.getDate()) &&
                            (newMeeting.getStartTime().equals(meeting.getStartTime()) ||
                                    newMeeting.getEndTime().equals(meeting.getEndTime()))) {
                        if (chooseMeeting(meeting, newMeeting) == 2) {
                            newCalendar.removeMeeting(meeting);
                            newCalendar.addMeeting(newMeeting);
                            newCalendar.addMeeting(changedMeeting(meeting));
                        }
                        else newCalendar.addMeeting(changedMeeting(newMeeting));
                    }
            }
        }
        return newCalendar;
    }

    private int chooseMeeting(Meeting meeting1, Meeting meeting2) {
        System.out.println("Choose a meeting to keep:" +
                "\n\n- Meeting 1:\n" + meeting1 +
                "\n- Meeting 2:\n" + meeting2);

        while (true) {
            String choice = new Scanner(System.in).toString().toLowerCase();
            switch (choice) {
                case "meeting 1": case "meeting_1": case "meeting1": case "1":
                    return 1;
                case "meeting 2": case "meeting_2": case "meeting2": case "2":
                    return 2;
                default:
                    System.out.println("Error: Wrong input.\n" +
                            "Examples: \"Meeting <number>\", \"Meeting_<number>\", \"Meeting<number>\" or just \"<number>\"\n");
                    break;
            }
        }
    }

    private Meeting changedMeeting(Meeting original) {
        while (true) {
            System.out.println("Enter new start and end times for other meeting");
            String[] userInput = new Scanner(System.in).toString().split(" ");

            if (userInput.length == 2) {
                Time newStartTime = new TimeParser().parse(userInput[0]);
                Time newEndTime = new TimeParser().parse(userInput[1]);
                if (newStartTime != null && newEndTime != null)
                    return new Meeting(original.getDate(), newStartTime, newEndTime, original.getName(), original.getNote());
            }
            else System.out.println("Error: " +
                    (userInput.length > 2 ? "Unnecessary arguments." : "Missing arguments.")
                    + "\nExample: \"<start_time> <end_time>\")\n");
        }
    }
}
