public class Deadline extends Task {
    private String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String formatForStorage() {
        return String.format("%s|%s|%s|%s", "D", super.isDone ? "1" : "0", this.name, this.by);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by %s)", super.toString(), this.by);
    }
}
