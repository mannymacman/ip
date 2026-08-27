package della.parser;

import della.command.Command;
import della.task.Deadline;
import della.task.Event;
import della.task.Todo;
import della.util.DateParser;
import java.time.LocalDateTime;

/**
 * Parses user input into commands, task details, and task objects.
 */
public class Parser {

    /**
     * Returns the command represented by the first word of the input.
     *
     * @param input User input containing a command and optional arguments.
     * @return Corresponding command, or {@code UNKNOWN} when the command is unrecognised.
     */
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

    /**
     * Returns the text following the command word in the input.
     *
     * @param input User input containing a command followed by arguments.
     * @return Arguments following the command word.
     */
    public static String parseArguments(String input) {
        // Returns everything after the command word.
        String[] inputParts = input.split("\\s+", 2);
        return inputParts[1];
    }

    /**
     * Returns the task number represented by the argument.
     *
     * @param argument Text expected to contain a whole-number task number.
     * @return Parsed task number.
     * @throws NumberFormatException If the argument is not a valid integer.
     */
    public static int parseTaskNumber(String argument) throws NumberFormatException {
        // Converts "3" into 3 for mark, unmark, and delete.
        return Integer.parseInt(argument);
    }

    /**
     * Returns a todo task created from the argument.
     *
     * @param argument Description of the todo task.
     * @return Todo task with the specified description.
     * @throws IllegalArgumentException If the description is empty.
     */
    public static Todo parseTodo(String argument) throws IllegalArgumentException {
        // Converts "read book" into a Todo object.
        if (argument.isEmpty()) {
            // catches if content is empty
            // eg. user enters "todo  " with trailing blank spaces
            throw new IllegalArgumentException("Cannot add empty todo task!");
        }
        return new Todo(argument);
    }

    /**
     * Returns a deadline task created from a description and a {@code /by} date.
     *
     * @param argument Deadline description followed by {@code /by dd/MM/yyyy HHmm}.
     * @return Deadline task with the specified description and date.
     * @throws IllegalArgumentException If the description or {@code /by} date is missing, or the date is in the past.
     * @throws java.time.format.DateTimeParseException If the date does not match the required format.
     */
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

    /**
     * Returns an event task created from a description, start time, and end time.
     *
     * @param argument Event description followed by {@code /from} and {@code /to} dates in {@code dd/MM/yyyy HHmm} format.
     * @return Event task with the specified description and time range.
     * @throws IllegalArgumentException If a required marker is missing, a date is in the past, or the end time is before the start time.
     * @throws java.time.format.DateTimeParseException If either date does not match the required format.
     */
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
