public class Todo extends Task {
    public Todo(String name) {
        super(name);
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
