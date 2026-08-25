public abstract class Task {
    protected String name;
    protected boolean isDone = false;

    public Task(String name) {
        this.name = name;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public abstract String formatForStorage();

    @Override
    public String toString() {
        if (isDone) {
            return String.format("[X] %s", this.name);
        } else {
            return String.format("[ ] %s", this.name);
        }
    }
}
