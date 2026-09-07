package della.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import della.task.Deadline;
import della.task.Event;
import della.task.Task;
import della.task.Todo;
import della.util.DateParser;

/**
 * Stores tasks in and retrieves tasks from a file.
 */
public class Storage {
    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";
    private static final String COMPLETED_STATUS = "1";
    private static final String STORAGE_DATE_TIME_FORMAT = "MMM dd yyyy h:mma";

    private final String filePath;

    /**
     * Creates storage that uses the specified file path.
     *
     * @param filePath Path of the file used to store tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Adds a task to the end of the storage file.
     *
     * @param task Task to store.
     * @throws IOException If the storage file cannot be written.
     */
    public void storeTask(Task task) throws IOException {
        try (FileWriter fileWriter = new FileWriter(this.filePath, true)) {
            fileWriter.write(task.formatForStorage());
            fileWriter.write("\n");
        }
    }

    /**
     * Returns whether the storage file exists and contains task data.
     *
     * @return {@code true} if the storage file contains stored tasks, otherwise {@code false}.
     */
    public boolean hasData() {
        File file = new File(this.filePath);
        return file.exists() && file.length() > 0;
    }

    /**
     * Returns tasks reconstructed from the storage file.
     *
     * @return Tasks read from the storage file in their stored order.
     * @throws FileNotFoundException If the storage file does not exist.
     * @throws java.time.format.DateTimeParseException If a stored deadline or event date has an invalid format.
     */
    public ArrayList<Task> loadTasks() throws FileNotFoundException {
        File storageFile = new File(this.filePath);
        ArrayList<Task> tasks = new ArrayList<>();

        try (Scanner scanner = new Scanner(storageFile)) {
            while (scanner.hasNext()) {
                tasks.add(parseTask(scanner.nextLine()));
            }
        }

        return tasks;
    }

    private Task parseTask(String taskLine) {
        String[] taskParts = taskLine.split("\\|");
        boolean isDone = taskParts[1].equals(COMPLETED_STATUS);

        if (taskParts[0].equals(TODO_TYPE)) {
            return new Todo(taskParts[2], isDone);
        } else if (taskParts[0].equals(DEADLINE_TYPE)) {
            return parseDeadline(taskParts, isDone);
        } else if (taskParts[0].equals(EVENT_TYPE)) {
            return parseEvent(taskParts, isDone);
        } else {
            throw new IllegalArgumentException(
                    "Invalid task type in storage: " + taskParts[0]);
        }
    }

    private Deadline parseDeadline(String[] taskParts, boolean isDone) {
        return new Deadline(
                taskParts[2],
                isDone,
                DateParser.parseDateTime(taskParts[3], STORAGE_DATE_TIME_FORMAT));
    }

    private Event parseEvent(String[] taskParts, boolean isDone) {
        return new Event(
                taskParts[2],
                isDone,
                DateParser.parseDateTime(taskParts[3], STORAGE_DATE_TIME_FORMAT),
                DateParser.parseDateTime(taskParts[4], STORAGE_DATE_TIME_FORMAT));
    }

    /**
     * Updates the stored task at the specified one-based task number.
     *
     * @param taskNum One-based position of the task to update.
     * @param task Updated task to store at the specified position.
     * @throws IOException If the storage file cannot be read or written.
     */
    public void updateTaskStatus(int taskNum, Task task) throws IOException {
        Path filePath = Path.of(this.filePath);
        List<String> taskLines = Files.readAllLines(filePath);
        int lineIndex = taskNum - 1;
        taskLines.set(lineIndex, task.formatForStorage());
        Files.write(filePath, taskLines);
    }

    /**
     * Deletes the stored task at the specified one-based task number.
     *
     * @param taskNum One-based position of the task to delete.
     * @throws IOException If the storage file cannot be read or written.
     */
    public void deleteTask(int taskNum) throws IOException {
        Path filePath = Path.of(this.filePath);
        List<String> taskLines = Files.readAllLines(filePath);
        int lineIndex = taskNum - 1;
        taskLines.remove(lineIndex);
        Files.write(filePath, taskLines);
    }
}
