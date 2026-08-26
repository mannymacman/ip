package della.parser;

import della.command.Command;
import della.task.Deadline;
import della.task.Event;
import della.task.Todo;
import della.util.DateParser;
import java.time.LocalDateTime;

public class Parser {

    public static Command parseCommand(String input) {
        // Returns Command Enum
        String[] inputParts = input.split("\\s+", 2);

        Command command;

        switch (inputParts[0]) {
            case "bye" -> command = Command.BYE;
            case "list" -> command = Command.LIST;
            case "mark" -> command = Command.MARK;
            case "unmark" -> command = Command.UNMARK;
            case "todo" -> command = Command.TODO;
            case "deadline" -> command = Command.DEADLINE;
            case "event" -> command = Command.EVENT;
            case "delete" -> command = Command.DELETE;
            default -> command = Command.UNKNOWN;
        }

        return command;
    }

    public static String parseArguments(String input) {
        // Returns everything after the command word.
        String[] inputParts = input.split("\\s+", 2);
        return inputParts[1];
    }

    public static int parseTaskNumber(String argument) throws NumberFormatException {
        // Converts "3" into 3 for mark, unmark, and delete.
        return Integer.parseInt(argument);
    }

    public static Todo parseTodo(String argument) throws IllegalArgumentException{
        // Converts "read book" into a Todo object.
        if (argument.isEmpty()) {
            // catches if content is empty
            // eg. user enters "todo  " with trailing blank spaces
            throw new IllegalArgumentException("Cannot add empty todo task!");
        }
        return new Todo(argument);
    }

    public static Deadline parseDeadline(String argument) throws IllegalArgumentException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("Bro, cannot add empty deadline!");
        }

        // Extracts description and /by date, then creates a Deadline.
        String[] argumentParts = argument.split("\\s+/by\\s+");

        if (argumentParts.length == 1) {
            throw new IllegalArgumentException("Missing or invalid /by command. Pls try again\"");
        }

        String taskName = argumentParts[0];
        String dateString = argumentParts[1];
        LocalDateTime dateTime = DateParser.parseDateTime(dateString, "dd/MM/yyyy HHmm");
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("DateTime entered cannot be before today");
        }
        return new Deadline(taskName, dateTime);
    }

    public static Event parseEvent(String argument) throws IllegalArgumentException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("Bro, cannot add empty event!");
        }
        // Extracts description, /from date, and /to date, then creates an Event.
        String[] argumentParts = argument.split("\\s+/from\\s+");

        if (argumentParts.length == 1) {
            throw new IllegalArgumentException("Missing or invalid /from command. Pls try again");
        }

        String taskName = argumentParts[0];
        String[] timeParts = argumentParts[1].split("\\s+/to\\s+");

        // throw customer exception if timeParts length == 1
        if (timeParts.length == 1) {
            throw new IllegalArgumentException("Missing or invalid /to command. Pls try again");
        }

        String fromDateString = timeParts[0];
        String toDateString = timeParts[1];
        LocalDateTime fromDateTime = DateParser.parseDateTime(fromDateString, "dd/MM/yyyy HHmm");
        LocalDateTime toDateTime = DateParser.parseDateTime(toDateString, "dd/MM/yyyy HHmm");
        if (fromDateTime.isBefore(LocalDateTime.now()) || fromDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("DateTime entered cannot be before today");
        }
        if (toDateTime.isBefore(fromDateTime)) {
            throw new IllegalArgumentException("/to DateTime must be before /by DateTime");
        }
        return new Event(taskName, fromDateTime, toDateTime);
    }
}
