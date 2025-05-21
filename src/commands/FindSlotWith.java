package commands;

import abstract_classes.SlotFinder;
import main_classes.Calendar;
import main_classes.CalendarLoader;
import main_classes.Meeting;
import main_classes.Program;
import parsers.LocalDateParser;

import java.time.LocalDate;
import java.util.*;

/**
 * The {@code FindSlotWith} class is a command, which finds an available slot for a
 * meeting, starting from a given date, checking one or more calendars.
 */
public class FindSlotWith extends SlotFinder {
    /**
     * Finds an available slot for a meeting starting from a given date, a given
     * amount of hours long, within one or more given calendars.
     * @param args Requires values for a starting date {@code from_date}, the
     * length of the meeting in {@code hours} and the calendars to be checked:
     * {@code calendarName1}, {@code calendarName2} etc.
     */
    @Override
    public void execute(String... args) {
        if (args.length < 3) {
            System.out.println("Error: Missing arguments.");
            System.out.println("Example input: \"findSlotWith <from_date> <hours> <calendar_name1> <calendar_name2>...\"");
            return;
        }

        LocalDate fromDate = new LocalDateParser().parse(args[0]);
        if (fromDate == null) return;

        int hours;
        try {
            hours = Integer.parseInt(args[1]);
            if (hours < 1 || hours > 9) {
                System.out.println("Error: Minimum 1 hour, maximum 9 hours.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entered value is not an integer.");
            return;
        }

        if (args[2].equals(Program.getMainCalendar().getName())) {
            System.out.println("Error: Tried opening an already opened calendar.");
            return;
        }

        Calendar[] calendars = new Calendar[args.length - 2];
        for (int i = 2; i < args.length; i++) {
            calendars[i - 2] = CalendarLoader.loadCalendar(args[i]);
            if (calendars[i - 2] == null) return;
        }

        findAvailableTime(fromDate, hours,
                combineMeetingsPerDay(calendars),
                combineHolidays(calendars));
    }

    /**
     * Combines the main calendar and all selected calendars' {@code meetingsPerDay}
     * maps into one {@code Map<LocalDate, List<Meeting>>} and returns it.
     * @param calendars The array with selected calendars.
     * @return The combined {@code meetingsPerDay} map.
     */
    private Map<LocalDate, List<Meeting>> combineMeetingsPerDay(Calendar[] calendars) {
        Map<LocalDate, List<Meeting>> combinedMeetingsPerDay = new LinkedHashMap<>(Program.getMainCalendar().getMeetingsPerDay());
        for (Calendar calendar : calendars) {
            Map<LocalDate, List<Meeting>> meetingsPerDay = calendar.getMeetingsPerDay();
            for (LocalDate date : meetingsPerDay.keySet()) {
                if (!combinedMeetingsPerDay.containsKey(date))
                    combinedMeetingsPerDay.put(date, new ArrayList<>());
                for (Meeting meeting : meetingsPerDay.get(date))
                    if (!combinedMeetingsPerDay.get(date).contains(meeting))
                        combinedMeetingsPerDay.get(date).add(meeting);
            }
        }

        List<Map.Entry<LocalDate, List<Meeting>>> sortedMapEntriesList = new ArrayList<>(combinedMeetingsPerDay.entrySet());
        sortedMapEntriesList.sort(Map.Entry.comparingByKey());

        combinedMeetingsPerDay.clear();
        for (Map.Entry<LocalDate, List<Meeting>> entry : sortedMapEntriesList)
            combinedMeetingsPerDay.put(entry.getKey(), entry.getValue());

        return combinedMeetingsPerDay;
    }

    /**
     * Combines the main calendar and all selected calendars' holidays
     * sets into one {@code Set<LocalDate>} and returns it.
     * @param calendars The array with selected calendars.
     * @return The combined holidays {@code Set<LocalDate>}.
     */
    private Set<LocalDate> combineHolidays(Calendar[] calendars) {
        Set<LocalDate> combinedHolidays = new HashSet<>(Program.getMainCalendar().getHolidays());
        for (Calendar calendar : calendars)
            combinedHolidays.addAll(calendar.getHolidays());
        return combinedHolidays;
    }
}
