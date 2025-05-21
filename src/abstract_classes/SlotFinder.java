package abstract_classes;

import interfaces.Command;
import main_classes.Meeting;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * The {@code SlotFinder} abstract class allows for different ways to find a free
 * slot for a new meeting within one or more calendars.
 */
public abstract class SlotFinder implements Command {
    /**
     * Finds and prints an available time for a new {@code Meeting}, a given amount
     * of hours long, capped from 1 to 9 hours, within one or more calendars,
     * starting from the given date.
     * @param fromDate The given starting date.
     * @param hours The length of the meeting in hours.
     * @param meetingsPerDay A list of all meetings per day.
     * @param holidays A {@code Set<LocalDate>} of all holidays.
     */
    protected static void findAvailableTime(
            LocalDate fromDate,
            int hours,
            Map<LocalDate, List<Meeting>> meetingsPerDay,
            Set<LocalDate> holidays) {

        Map<LocalDate, List<Meeting>> sortedMeetingsPerDay = getSortedMeetingPerDay(meetingsPerDay);

        LocalTime availableStartTime = null;
        LocalTime possibleStartTime = LocalTime.parse("08:00:00");
        LocalTime endOfDay = LocalTime.parse("17:00:00");

        while (availableStartTime == null) {
            DayOfWeek dayOfWeek = LocalDate.parse(fromDate.toString()).getDayOfWeek();
            if ((dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) || holidays.contains(fromDate)) {
                fromDate = fromDate.plusDays(1);
                continue;
            }

            if (!meetingsPerDay.containsKey(fromDate)) {
                System.out.println("A meeting lasting " + hours + " hours can be set at any time on " + fromDate + ".");
                return; //Exit point 1
            }

            for (Meeting meeting : sortedMeetingsPerDay.get(fromDate)) {
                LocalTime startTime = meeting.getStartTime();
                LocalTime endTime = meeting.getEndTime();

                int hoursToSubtract = possibleStartTime.getHour() + (startTime.getMinute() >= possibleStartTime.getMinute() ? 0 : 1);
                if (startTime.minusHours(hoursToSubtract).getHour() >= hours) {
                    availableStartTime = possibleStartTime;
                    break; //Exit point 2
                }

                hoursToSubtract = endTime.getHour() + (endTime.getMinute() > 0 ? 1 : 0);
                if (endOfDay.minusHours(hoursToSubtract).getHour() >= hours) {
                    possibleStartTime = endTime;
                    continue;
                }

                fromDate = fromDate.plusDays(1);
                possibleStartTime = LocalTime.parse("08:00:00");
                break;
            }
        }

        System.out.println("A meeting lasting " + hours + (hours == 1 ? " hour" : " hours") +
                " can be set on " + fromDate + " at " + availableStartTime + ".");
    }

    /**
     * Sorts the map with all meetings per date.
     * @param meetingsPerDay The map with all meetings per date
     * @return The sorted map.
     */
    private static Map<LocalDate, List<Meeting>> getSortedMeetingPerDay(Map<LocalDate, List<Meeting>> meetingsPerDay) {
        Map<LocalDate, List<Meeting>> sortedMeetingsPerDay = new LinkedHashMap<>();

        List<Map.Entry<LocalDate, List<Meeting>>> meetingsPerDayList = new ArrayList<>(meetingsPerDay.entrySet());
        meetingsPerDayList.sort(Map.Entry.comparingByKey());

        for (Map.Entry<LocalDate, List<Meeting>> entry : meetingsPerDayList) {
            entry.getValue().sort(Comparator.comparing(Meeting::getDate).thenComparing(Meeting::getStartTime));
            sortedMeetingsPerDay.put(entry.getKey(), entry.getValue());
        }

        return sortedMeetingsPerDay;
    }
}
