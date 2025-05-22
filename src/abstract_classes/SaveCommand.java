package abstract_classes;

import interfaces.Command;
import main_classes.Meeting;
import main_classes.Program;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The {@code SaveCommand} abstract class is basically an interface for all different
 * save type commands, containing a method which opens the necessary files, writes
 * the needed data and closes the files.
 */
public abstract class SaveCommand implements Command {
    /**
     * Saves the currently open calendar into an existing or new file.
     * <p>
     *     Opens the file of the calendar with the given name, if it exists, saves the
     *     meetings and holidays, closes the file and returns {@code true} if the
     *     operation is successful, otherwise returns {@code false}.
     * </p>
     * @param calendarName The name of the calendar.
     * @return {@code True} if the calendar is successfully saved, {@code false} if the
     * operation has failed.
     */
    protected boolean saveCalendar(String calendarName) {
        try (FileWriter fileWriter = new FileWriter("..\\calendars\\" + calendarName + "\\meetings.txt")) {
            for (Meeting meeting : Program.getMainCalendar().getMeetings()) {
                fileWriter
                        .append("Date: ").append(meeting.getDate().toString()).append('\n')
                        .append("Start time: ").append(meeting.getStartTime().toString()).append('\n')
                        .append("End time: ").append(meeting.getEndTime().toString()).append('\n')
                        .append("Name: ").append(meeting.getName()).append('\n')
                        .append("Note: ").append(meeting.getNote()).append("\n\n");
            }
        } catch (IOException e) {
            System.out.println("Error: Cannot access meetings file.");
            return false;
        }

        try (FileWriter fileWriter = new FileWriter("..\\calendars\\" + calendarName + "\\holidays.txt")) {
            for (LocalDate holiday : Program.getMainCalendar().getHolidays())
                fileWriter.append("Date: ").append(holiday.toString()).append('\n');
        } catch (IOException e) {
            System.out.println("Error: Cannot access holidays file.");
            return false;
        }

        return true;
    }
}
