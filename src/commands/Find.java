package commands;

import main_classes.Meeting;
import interfaces.Command;
import main_classes.Program;

/**
 * The {@code Find} class is a command, which finds and prints all meetings, whose
 * name or note matches the given name or note.
 */
public class Find implements Command {
    /**
     * Finds and prints all meetings, whose name or note matches the given value.
     * @param args Requires only a name or a note.
     */
    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            System.out.println(args.length > 1 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example: \"Input> find <name_or_note>\"");
            System.out.println("Note: For meetings with no notes - \"Input> find empty\"");
            return;
        }

        findMeetings(args[0].replace('_', ' '));
    }

    /**
     * Finds and prints the meetings.
     * @param value The given name or note.
     */
    private void findMeetings(String value) {
        boolean found = false;
        for (Meeting meeting : Program.getMainCalendar().getMeetings()) {
            if (value.equalsIgnoreCase("empty"))
                if (meeting.getNote().isEmpty()) {
                    found = true;
                    System.out.println("\n" + meeting.getDescription());
                }
            if (meeting.getName().equals(value) || meeting.getNote().equals(value)) {
                found = true;
                System.out.println("\n" + meeting.getDescription());
            }
        }
        if (!found)
            System.out.println("No meetings with name or note \"" + value + "\"found.");
    }
}
