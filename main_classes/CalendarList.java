package main_classes;

import java.util.HashMap;
import java.util.Map;

public class CalendarList {
    private static Map<String, Calendar> calendars = new HashMap<>();

    public static void addCalendar(String calendarName, Calendar calendar) {
        calendars.put(calendarName, calendar);
    }

    public static void removeCalendar(String calendarName) {
        calendars.remove(calendarName);
    }

    public static Calendar findCalendar(String calendarName) {
        for (Map.Entry<String, Calendar> entry : calendars.entrySet())
            if (entry.getKey().equals(calendarName))
                return entry.getValue();
        System.out.println("Calendar with the name \"" + calendarName + "\" was not found!\n");
        return null;
    }

    public static void showAllCalendars() {
        for (Map.Entry<String, Calendar> entry : calendars.entrySet())
            System.out.println("- " + entry.getKey());
        System.out.println();
    }
}
