package main_classes;

import parsers.LocalDateParser;
import parsers.LocalTimeParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * The {@code CalendarLoader} class loads a calendar with the given name, if such
 * calendar exists.
 */
public class CalendarLoader {
    /**
     * Loads the calendar with the given name.
     * @param calendarName The name of the calendar to be loaded.
     * @return An object of the loaded calendar.
     */
    public static Calendar loadCalendar(String calendarName) {
        Calendar calendar = new Calendar(calendarName);

        if (!new File("calendars").exists()) {
            System.out.println("Error: No calendars found.");
            return null;
        }

        if (!new File("calendars\\" + calendarName).exists()) {
            System.out.println("Error: Calendar \"" + calendarName + "\" not found.");
            return null;
        }

        calendar = loadMeetings(calendar);
        if (calendar == null) return null;
        calendar = loadHolidays(calendar);

        return calendar;
    }

    /**
     * Loads all meetings from the calendar's meetings file into the program.
     * @param calendar The selected calendar.
     * @return The calendar with loaded meetings.
     */
    private static Calendar loadMeetings(Calendar calendar) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("calendars\\" + calendar.getName() + "\\meetings.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }

        List<String> lines = new ArrayList<>();
        while (scanner.hasNext())
            lines.add(scanner.nextLine());
        scanner.close();

        String[] linesArray = lines.toArray(new String[0]);
        try {
            for (int j = 0; j < linesArray.length; j += 6) {
                LocalDate date = new LocalDateParser().parse(linesArray[j].substring(6));
                if (date == null) return null;
                LocalTime startTime = new LocalTimeParser().parse(linesArray[j + 1].substring(12));
                if (startTime == null) return null;
                LocalTime endTime = new LocalTimeParser().parse(linesArray[j + 2].substring(10));
                if (endTime == null) return null;
                calendar.addMeeting(new Meeting(date, startTime, endTime, linesArray[j + 3].substring(6), linesArray[j + 4].substring(6)));
            }
            calendar.sortMeetings();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Problem with reading data.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: An error occurred.");
            return null;
        }

        return calendar;
    }

    /**
     * Loads all holidays from the calendar's holidays file into the program.
     * @param calendar The selected calendar.
     * @return The calendar with loaded holidays.
     */
    private static Calendar loadHolidays(Calendar calendar) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("calendars\\" + calendar.getName() + "\\holidays.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
            return null;
        }

        while (scanner.hasNext()) {
            LocalDate holiday = new LocalDateParser().parse(scanner.nextLine().substring(6));
            if (holiday == null) {
                System.out.println("Error: Invalid data.\n");
                return null;
            }
            calendar.addHoliday(holiday);
        }
        calendar.sortHolidays();
        scanner.close();

        return calendar;
    }
}
