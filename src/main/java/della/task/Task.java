package della.task;

/**
 * Represents a task with a name and completion status.
 */
public abstract class Task {
    /**
     * Stores the description of this task.
     */
    protected String name;

    /**
     * Stores whether this task is complete.
     */
    protected boolean isDone = false;

    /**
     * Creates an incomplete task with the specified name.
     *
     * @param name Description of the task.
     */
    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Marks this task as complete.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns this task in a format suitable for storage.
     *
     * @return Storage representation of this task.
     */
    public abstract String formatForStorage();

    /**
     * Returns a display representation of this task and its completion status.
     *
     * @return Display representation of this task.
     */
    @Override
    public String toString() {
        if (isDone) {
            return String.format("[X] %s", this.name);
        } else {
            return String.format("[ ] %s", this.name);
        }
    }
}
