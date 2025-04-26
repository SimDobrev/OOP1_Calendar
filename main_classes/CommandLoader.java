package main_classes;


import commands.Agenda;
import commands.Book;
import commands.Change;
import commands.Unbook;
import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandLoader {
    public static Map<String, Command> loadCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("book", new Book());
        commands.put("unbook", new Unbook());
        commands.put("agenda", new Agenda());
        commands.put("change", new Change());
        //find
        //holiday
        //busyDays
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
}
