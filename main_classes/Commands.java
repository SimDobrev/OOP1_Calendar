package main_classes;

import basic_commands.*;
import commands.*;
import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Commands} class contains a {@code Map} with all currently available
 * commands.
 */
public class Commands {
    /**
     * A {@code Map<String, Command>} which contains all currently available commands.
     * <p> Every command is accessed via unique keyword. </p>
     */
    private static Map<String, Command> commands = new HashMap<>();

    /**
     * Loads all currently available commands into the static
     * {@code Map<String, Command>} attribute and returns it.
     * @return The static {@code Map<String, Command>} with all available commands.
     */
    public static Map<String, Command> load() {
        commands.put("open", new Open());
        commands.put("save", new Save());
        commands.put("saveas", new SaveAs());
        commands.put("close", new Close());
        commands.put("help", new Help());
        commands.put("showcalendars", new ShowCalendars());
        commands.put("create", new Create());
        commands.put("delete", new Delete());

        commands.put("book", new Book());
        commands.put("unbook", new Unbook());
        commands.put("agenda", new Agenda());
        commands.put("change", new Change());
        commands.put("find", new Find());
        commands.put("holiday", new Holiday());
        commands.put("unsetholiday", new UnsetHoliday());
        commands.put("busydays", new BusyDays());
        commands.put("findslot", new FindSlot());
        commands.put("findslotwith", new FindSlotWith());
        commands.put("merge", new Merge());
        return commands;
    }
}
