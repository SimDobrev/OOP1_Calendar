package commands;

import main_classes.Program;
import parsers.LocalDateParser;
import main_classes.Meeting;
import interfaces.Command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Agenda} class is a command, which prints a list of all the meetings on a given date.
 */
public class Agenda implements Command {
    /**
     * Prints a {@code List} of all meetings on a given date.
     * @param args Requires only the given date.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example input: \"agenda <date>\"");
            return;
        }

        LocalDate date = new LocalDateParser().parse(args[0]);
        if (date == null) return;

        if (Program.getMainCalendar().containsHoliday(date)) {
            System.out.println("Error: Given day is set as a holiday.");
            return;
        }

        List<Meeting> agenda = getAgenda(date);
        if (!agenda.isEmpty())
            for (Meeting meeting : agenda)
                System.out.println('\n' + meeting.getDescription());
        else System.out.println("Error: No meetings set on this day.");
    }

    /**
     * Gets and returns a {@code  List<Meeting>} with all meetings on the given date.
     * @param date The given date.
     * @return A list with all meetings on the given date
     */
    private List<Meeting> getAgenda(LocalDate date) {
        List<Meeting> found = new ArrayList<>();
        for (Meeting meeting : Program.getMainCalendar().getMeetings())
            if (date.equals(meeting.getDate()))
                found.add(meeting);
        return found;
    }
}
