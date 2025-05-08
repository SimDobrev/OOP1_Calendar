package main_classes;

import basic_commands.*;
import commands.*;
import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private static Map<String, Command> commands = new HashMap<>();

    public static Map<String, Command> loadCommands() {
        commands.put("book", new Book());
        commands.put("unbook", new Unbook());
        commands.put("agenda", new Agenda());
        commands.put("change", new Change());
        commands.put("find", new Find());
        commands.put("holiday", new Holiday());
        commands.put("busy_days", new BusyDays());
        commands.put("busydays", new BusyDays());
        //findSlot
        //findSlotWith
        commands.put("merge", new Merge());

        commands.put("open", new Open());
        commands.put("save", new Save());
        commands.put("saveas", new SaveAs());
        commands.put("close", new Close());
        commands.put("help", new Help());
        commands.put("showcalendars", new ShowCalendars());
        //create
        //delete
        return commands;
    }

    public static Map<String, Command> get() {
        return commands;
    }
}
