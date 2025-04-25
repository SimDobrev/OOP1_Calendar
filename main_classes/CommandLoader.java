package main_classes;

import commands.Book;
import commands.Unbook;
import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandLoader {
    public static Map<String, Command> loadCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("book", new Book());
        commands.put("unbook", new Unbook());
        //agenda
        //change
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
