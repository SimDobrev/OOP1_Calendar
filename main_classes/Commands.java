package main_classes;

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
        //findsLot
        //findsLotWith
        //merge

        //open
        //save
        //saveAs
        //close
        //help
        //exit
        return commands;
    }

    public static Map<String, Command> get() {
        return commands;
    }
}
