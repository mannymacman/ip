package della.task;

import della.util.DateParser;
import java.time.LocalDateTime;

/**
 * Represents a task that occurs within a specified time range.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an incomplete event task with the specified name and time range.
     *
     * @param name Description of the event task.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an event task with the specified name, completion status, and time range.
     *
     * @param name Description of the event task.
     * @param isDone Completion status of the event task.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String name, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.isDone = isDone;
        this.from = from;
        this.to = to;
    }

    @Override
    public String formatForStorage() {
        return String.format(
                "%s|%s|%s|%s|%s", "E", super.isDone ? "1" : "0",
                this.name, DateParser.printDateTime(this.from), DateParser.printDateTime(this.to));
    }

    @Override
    public String toString() {
        return String.format(
                "[E]%s (from: %s to: %s)",
                super.toString(), DateParser.printDateTime(this.from), DateParser.printDateTime(this.to));
    }
}
