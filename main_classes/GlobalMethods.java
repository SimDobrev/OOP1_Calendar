package main_classes;

import parsers.DateParser;
import parsers.TimeParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GlobalMethods {
    public static void createCalendar(String calendarName) {
        try {
            if (new File("calendars" + calendarName).exists()) {
                System.out.println("Error: Calendar \"" + calendarName + "\" already exists.\n");
                return;
            }
            new File("calendars").mkdir();
            new File("calendars\\" + calendarName).mkdir();
            new File("calendars\\" + calendarName + "\\meetings.txt").createNewFile();
            new File("calendars\\" + calendarName + "\\holidays.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("Error: Calendar could not be created.\n");
            return;
        }
        System.out.println("Calendar successfully created.\n");
    }

    public static void deleteCalendar(String calendarName) {
        try {
            if (!new File("calendars").exists()) {
                System.out.println("Error: No calendars found.\n");
                return;
            }

            if (!new File("calendars" + calendarName).exists()) {
                System.out.println("Error: Calendar \"" + calendarName + "\"not found.\n");
                return;
            }

            new File("calendars\\" + calendarName + "\\meetings.txt").delete();
            new File("calendars\\" + calendarName + "\\holidays.txt").delete();
            new File("calendars\\" + calendarName).delete();
        } catch (RuntimeException e) {
            System.out.println("Error: Calendar could not be deleted.\n");
            return;
        }
        Calendar.set(null);
        System.out.println("Calendar successfully deleted.\n");
    }

    public static boolean saveCalendar(String path) {
        try (FileWriter fileWriter = new FileWriter(path + "\\meetings.txt")) {
            for (Meeting meeting : Calendar.get().getMeetings()) {
                fileWriter
                        .append("Date: ").append(meeting.getDate().toString()).append('\n')
                        .append("Start time: ").append(meeting.getStartTime().toString()).append('\n')
                        .append("End time: ").append(meeting.getEndTime().toString()).append('\n')
                        .append("Name: ").append(meeting.getName()).append('\n')
                        .append("Note: ").append(meeting.getNote()).append("\n\n");
            }
        } catch (IOException e) {
            System.out.println("Error: Cannot access meetings file.\n");
            return false;
        }

        try (FileWriter fileWriter = new FileWriter(path + "\\holidays.txt")) {
            for (Date holiday : Calendar.get().getHolidays())
                fileWriter.append("Date: ").append(holiday.toString()).append('\n');
        } catch (IOException e) {
            System.out.println("Error: Cannot access holidays file.\n");
            return false;
        }

        return true;
    }

    public static Calendar loadCalendar(String calendarName) {
        if (!Calendar.get().getMeetings().isEmpty() || !Calendar.get().getHolidays().isEmpty()) {
            System.out.println("Error: Calendar is not empty.\n");
            return null;
        }

        if (!new File("calendars").exists()) {
            System.out.println("Error: No calendars found.\n");
            return null;
        }

        if (!new File("Calendars" + calendarName).exists()) {
            System.out.println("Error: Calendar \"" + calendarName + "\"not found.\n");
            return null;
        }

        Calendar calendar = loadMeetings(Calendar.get());
        if (calendar == null) return null;
        calendar = loadHolidays(calendar);
        if (calendar == null) return null;

        Calendar.set(calendar);
        return calendar;
    }

    private static Calendar loadMeetings(Calendar calendar) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("calendars\\" + calendar.getName() + "\\meetings.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
            return null;
        }

        List<String> lines = new ArrayList<>();
        while (scanner.hasNext())
            lines.add(scanner.nextLine());

        String[] linesArray = lines.toArray(new String[0]);
        for (int j = 0; j < linesArray.length; j += 6)
            calendar.addMeeting(new Meeting(
                    new DateParser().parse(linesArray[j]),
                    new TimeParser().parse(linesArray[j+1]),
                    new TimeParser().parse(linesArray[j+2]),
                    linesArray[j + 3],
                    linesArray[j + 4]
            ));

        return calendar;
    }

    private static Calendar loadHolidays(Calendar calendar) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("calendars\\" + calendar.getName() + "\\holidays.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
            return null;
        }

        while (scanner.hasNext())
            calendar.addHoliday(new DateParser().parse(scanner.nextLine()));

        return calendar;
    }
}
