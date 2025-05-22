package commands;

import basic_commands.*;
import main_classes.Calendar;
import main_classes.CalendarLoader;
import main_classes.Meeting;
import interfaces.Command;
import main_classes.Program;
import parsers.LocalDateParser;
import parsers.LocalTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * The {@code Merge} class is a command, which merges the currently open calendar
 * with one or more selected calendars.
 */
public class Merge implements Command {
    /**
     * Merges the currently open calendar with the given ones by combining
     * all meetings and holidays. Deletes all given calendars, including the
     * main calendar and creates a new calendar with the main calendar's name.
     * @param args Requires one or more calendar names.
     */
    @Override
    public void execute(String... args) {
        if (args.length < 1) {
            System.out.println("Error: Missing arguments.\n" +
                    "Example: \"merge <calendar1_name> <calendar2_name>...\"");
            return;
        }

        Calendar[] calendars = new Calendar[args.length];
        for (int i = 0; i < calendars.length; i++) {
            calendars[i] = CalendarLoader.loadCalendar(args[i]);
            if (calendars[i] == null) return;
        }

        String mainCalendarName = Program.getMainCalendar().getName();
        boolean successfullyMerged = merge(calendars);
        if (successfullyMerged) {
            System.out.println();
            Program.setMainCalendar(null);
            new Delete().execute(mainCalendarName);
            for (Calendar calendar : calendars)
                new Delete().execute(calendar.getName());
            new Create().execute(mainCalendarName);
            Program.setMainCalendar(CalendarLoader.loadCalendar(mainCalendarName));
        }
        else {
            System.out.println("Error: Calendars could not be merged.");
            return;
        }

        System.out.println("Calendars successfully merged.");
        System.out.println("NOTE: Don't forget to save the new merged calendar!");
    }

    /**
     * Merges the calendars by combining all meetings and holidays.
     * @param calendars All selected calendars.
     * @return {@code true} if the calendars are merged successfully, {@code false}
     * if the operation failed.
     */
    private boolean merge(Calendar[] calendars) {
        Calendar mergedCalendar = Program.getMainCalendar();
        for (Calendar calendar : calendars) {
            for (LocalDate holiday : calendar.getHolidays()) {
                if (mergedCalendar.getHolidays().contains(holiday)) continue;
                mergedCalendar.addHoliday(holiday);
            }
            calendar.sortHolidays();

            for (Meeting meeting : calendar.getMeetings()) {
                if (mergedCalendar.containsMeeting(meeting)) continue;
                mergedCalendar.addMeeting(meeting);
            }
            mergedCalendar.sortMeetings();
        }

        checkMeetings(mergedCalendar);
        mergedCalendar.sortHolidays();
        mergedCalendar.sortMeetings();

        return true;
    }

    /**
     * Checks all meetings for interceptions or copies and removes them.
     * @param mergedCalendar The merged calendar.
     */
    private void checkMeetings(Calendar mergedCalendar) {
        List<Meeting> meetingsToRemove = new ArrayList<>();
        Map<Meeting, Meeting> meetingsToReplace = new HashMap<>();
        Meeting previousMeeting = null;
        for (Meeting currentMeeting : mergedCalendar.getMeetings()) {
            if (previousMeeting == null) {
                previousMeeting = currentMeeting;
                continue;
            }

            if (previousMeeting.equals(currentMeeting)) {
                meetingsToRemove.add(currentMeeting);
                continue;
            }

            LocalTime prevStartTime = previousMeeting.getStartTime(), currStartTime = currentMeeting.getStartTime();
            LocalTime prevEndTime = previousMeeting.getEndTime(), currEndTime = currentMeeting.getEndTime();

            if ((prevStartTime.isBefore(currStartTime) && prevEndTime.isAfter(currStartTime))
                    || (!prevStartTime.isBefore(currStartTime) && prevEndTime.isBefore(currEndTime))) {
                int result = chooseMeeting(previousMeeting, currentMeeting);
                meetingsToReplace.put(
                        result == 1 ? currentMeeting : previousMeeting,
                        changeMeeting(result == 1 ? currentMeeting : previousMeeting, mergedCalendar)
                );
            }

            previousMeeting = currentMeeting;
        }

        for (Meeting meeting : meetingsToRemove)
            mergedCalendar.removeMeeting(meeting);

        for (Meeting meeting : meetingsToReplace.keySet()) {
            mergedCalendar.removeMeeting(meeting);
            mergedCalendar.addMeeting(meetingsToReplace.get(meeting));
        }
    }

    /**
     * Prompts the user to choose which of the two given meetings to keep. Whichever
     * meeting is chosen will be kept and the method will prompt the user to give
     * new valid values for the other meeting.
     * @param meeting1 The first meeting.
     * @param meeting2 The second meeting.
     * @return {@code 1} if the first meeting is chosen, {@code 2} if the second
     * meeting is chosen.
     */
    private int chooseMeeting(Meeting meeting1, Meeting meeting2) {
        System.out.println("Choose a meeting to keep:" +
                "\n\n- Meeting 1:\n" + meeting1 +
                "\n\n- Meeting 2:\n" + meeting2);

        while (true) {
            System.out.print("\nInput> ");
            String choice = new Scanner(System.in).nextLine().toLowerCase();
            switch (choice) {
                case "meeting 1": case "meeting_1": case "meeting1": case "1":
                    return 1;
                case "meeting 2": case "meeting_2": case "meeting2": case "2":
                    return 2;
                default:
                    System.out.println("Error: Invalid input.");
                    System.out.println("Examples: \"Meeting <number>\", \"Meeting_<number>\", \"Meeting<number>\" or just \"<number>\"");
                    break;
            }
        }
    }

    /**
     * Prompts the user to enter new values and if valid, the method checks whether
     * the changed meeting fits within the merged calendar. If {@code true} the
     * changed meeting is returned, if {@code false} the user is prompted to enter
     * new values again.
     * @param original The original meeting.
     * @param mergedCalendar The merged calendar.
     * @return The changed meeting.
     */
    private Meeting changeMeeting(Meeting original, Calendar mergedCalendar) {
        LocalDate newDate = null;
        while (newDate == null) {
            System.out.print("\nEnter new date (or same date) for the other meeting:\nInput> ");
            String userInput = new Scanner(System.in).nextLine();
            newDate = new LocalDateParser().parse(userInput);
        }

        while (true) {
            System.out.print("\nNow enter new start and end times for the other meeting:\nInput> ");
            String[] userInput = new Scanner(System.in).nextLine().split(" ");

            if (userInput.length == 2) {
                LocalTime newStartTime = new LocalTimeParser().parse(userInput[0]);
                LocalTime newEndTime = new LocalTimeParser().parse(userInput[1]);
                if (newStartTime != null && newEndTime != null) {
                    if (areValidTimes(newDate, newStartTime, newEndTime, mergedCalendar))
                        return new Meeting(newDate, newStartTime, newEndTime, original.getName(), original.getNote());
                    else System.out.println("New times do not fit within that day's schedule.");
                }
            }
            else System.out.println("Error: " +
                    (userInput.length > 2 ? "Unnecessary arguments." : "Missing arguments.")
                    + "\nExample input: \"<start_time> <end_time>\"");
        }
    }

    /**
     * Checks whether a meeting with the given start and end times fits within
     * the given date's schedule. Returns {@code true} if it fits, {@code false}
     * if not.
     * @param date The given date.
     * @param startTime The proposed start time.
     * @param endTime The proposed end time.
     * @return {@code true} if the meeting fits within the schedule, {@code false}
     * if not.
     */
    private boolean areValidTimes(LocalDate date, LocalTime startTime, LocalTime endTime, Calendar mergedCalendar) {
        Map<LocalDate, List<Meeting>> meetingsPerDay = mergedCalendar.getMeetingsPerDay();
        if (!meetingsPerDay.containsKey(date)) return true;

        int index = 1;
        for (Meeting meeting : meetingsPerDay.get(date)) {
            if (startTime.isBefore(meeting.getStartTime()))
                return !endTime.isAfter(meeting.getStartTime());

            if (!startTime.isBefore(meeting.getEndTime())) {
                if (index == meetingsPerDay.get(date).size()) return true;
                else index++;
            }
            else break;
        }
        return false;
    }
}
