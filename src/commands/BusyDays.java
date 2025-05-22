package commands;

import main_classes.Program;
import parsers.LocalDateParser;
import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * The {@code BusyDays} class is a command, which prints a list of all days with
 * meetings within the given range, ordered by the amount of busy hours.
 */
public class BusyDays implements Command {
    /**
     * Gets all days with meetings from the first given date to the second given
     * date and prints them, ordered by the amount of busy hours per day.
     * @param args Requires values for starting date - {@code date_from} and end
     * date - {@code date_to}.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing arguments.");
            System.out.println("Example input: \"busyDays <date_from> <date_to>\"");
            return;
        }

        LocalDate dateFrom = new LocalDateParser().parse(args[0]);
        if (dateFrom == null) return;
        LocalDate dateTo = new LocalDateParser().parse(args[1]);
        if (dateTo == null) return;

        if (dateFrom.isBefore(dateTo))
            listBusyDays(Program.getMainCalendar(), dateFrom, dateTo);
        else System.out.println("Error: Starting date cannot be later than or equal to the final date.");
    }

    /**
     * Prints the list of all days with meetings within the given range ordered by
     * the amount of busy hours.
     * @param calendar The given calendar.
     * @param date_from The starting date.
     * @param date_to The end date.
     */
    private void listBusyDays(Calendar calendar, LocalDate date_from, LocalDate date_to) {
        List<Meeting> meetingsInRange = new ArrayList<>(calendar.getMeetings());
        meetingsInRange.removeIf(meeting -> meeting.getDate().isBefore(date_from) ||
                meeting.getDate().isAfter(date_to));

        Map<LocalDate, LocalTime> hoursPerDay = getHoursPerDay(meetingsInRange);
        if (hoursPerDay.isEmpty()) {
            System.out.println("No busy days found.");
            return;
        }

        List<Map.Entry<LocalDate, LocalTime>> entryList = new ArrayList<>(hoursPerDay.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        for (Map.Entry<LocalDate, LocalTime> entry : entryList)
            System.out.print("\n- Total busy hours on " + entry.getKey() + " - " + entry.getValue() + " hours");
        System.out.println();
    }

    /**
     * Creates and returns a {@code Map<LocalDate, LocalTime>} with dates as the keys and the
     * total busy hours for each date.
     * @param meetings A list of all meetings within the given range.
     * @return A {@code Map<LocalDate, LocalTime>} with dates and total busy hours.
     */
    private Map<LocalDate, LocalTime> getHoursPerDay(List<Meeting> meetings) {
        Map<LocalDate, LocalTime> meetingsPerDay = new HashMap<>();

        LocalTime totalTime = LocalTime.parse("00:00:00");
        LocalDate previousDate = meetings.get(0).getDate();
        for (Meeting meeting : meetings) {
            if (!meeting.getDate().equals(previousDate)) {
                meetingsPerDay.put(previousDate, totalTime);
                previousDate = meeting.getDate();
                totalTime = LocalTime.parse("00:00:00");
            }

            LocalTime startTime = meeting.getStartTime();
            LocalTime endTime = meeting.getEndTime();
            LocalTime meetingTime = endTime
                    .minusHours(startTime.getHour())
                    .minusMinutes(startTime.getMinute())
                    .minusSeconds(startTime.getSecond());

            totalTime = totalTime.plusHours(meetingTime.getHour())
                    .plusHours(meetingTime.getMinute())
                    .plusSeconds(meetingTime.getSecond());
        }
        meetingsPerDay.put(previousDate, totalTime);

        return meetingsPerDay;
    }
}
