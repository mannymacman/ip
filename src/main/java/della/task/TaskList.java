package della.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and manages the chatbot's tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing tasks loaded from storage.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the specified zero-based index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified zero-based index.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Marks the specified task as complete and returns it.
     */
    public Task mark(int index) {
        Task task = tasks.get(index);
        task.mark();
        return task;
    }

    /**
     * Marks the specified task as incomplete and returns it.
     */
    public Task unmark(int index) {
        Task task = tasks.get(index);
        task.unmark();
        return task;
    }

    /**
     * Returns the number of tasks currently stored.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a copy of the tasks for displaying or saving.
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(List.copyOf(tasks));
    }
}
