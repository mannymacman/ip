import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String name, LocalDateTime by) {
        super(name);
        this.by = by;
    }

    public Deadline(String name, boolean isDone, LocalDateTime by) {
        super(name);
        this.isDone = isDone;
        this.by = by;
    }

    @Override
    public String formatForStorage() {
        return String.format("%s|%s|%s|%s", "D", super.isDone ? "1" : "0", this.name, DateParser.printDateTime(this.by));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by %s)", super.toString(), DateParser.printDateTime(this.by));
    }
}
