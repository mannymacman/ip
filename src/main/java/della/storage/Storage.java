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
                String taskLine = scanner.nextLine();
                String[] taskParts = taskLine.split("\\|");
                if (taskParts[0].equals("T")) {
                    tasks.add(new Todo(taskParts[2], taskParts[1].equals("1")));
                } else if (taskParts[0].equals("D")) {
                    tasks.add(
                            new Deadline(
                                    taskParts[2],
                                    taskParts[1].equals("1"),
                                    DateParser.parseDateTime(taskParts[3], "MMM dd yyyy h:mma")));
                } else {
                    tasks.add(
                            new Event(
                                    taskParts[2],
                                    taskParts[1].equals("1"),
                                    DateParser.parseDateTime(taskParts[3], "MMM dd yyyy h:mma"),
                                    DateParser.parseDateTime(taskParts[4], "MMM dd yyyy h:mma")));
                }
            }
        }

        return tasks;
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
