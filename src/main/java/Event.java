import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String name, LocalDateTime from, LocalDateTime to) throws InvalidEventDateException {
        super(name);
        if (to.isBefore(from)) {
            throw new InvalidEventDateException();
        }
        this.from = from;
        this.to = to;
    }

    public Event(String name, boolean isDone, LocalDateTime from, LocalDateTime to) throws InvalidEventDateException {
        super(name);
        if (to.isBefore(from)) {
            throw new InvalidEventDateException();
        }
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
