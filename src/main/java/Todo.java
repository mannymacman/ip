public class Todo extends Task {
    public Todo(String name) {
        super(name);
    }

    public Todo(String name, boolean isDone) {
        super(name);
        this.isDone = isDone;
    }

    @Override
    public String formatForStorage() {
        return String.format("%s|%s|%s", "T", super.isDone ? "1" : "0", this.name);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
