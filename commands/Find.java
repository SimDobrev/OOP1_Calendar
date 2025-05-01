package commands;

import main_classes.Calendar;
import main_classes.Meeting;
import interfaces.Command;

public class Find implements Command {
    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println(args.length > 2 ?
                    "Error: Unnecessary arguments." :
                    "Error: Missing argument.");
            System.out.println("Example: \"Input> find <name_or_note>\"\n");
            return;
        }

        findMeetings(args[1]);
    }

    private void findMeetings(String value) {
        boolean found = false;
        for (Meeting meeting : Calendar.get().getMeetings())
            if (meeting.getName().equals(value) || meeting.getNote().equals(value)) {
                found = true;
                System.out.println(meeting);
            }
        if (!found) System.out.println("No meetings found.\n");
    }
}
