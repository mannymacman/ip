package della.storage;

import della.task.Deadline;
import della.task.Event;
import della.task.Task;
import della.task.Todo;
import della.util.DateParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void storeTask(Task task) throws IOException {
        FileWriter fw = new FileWriter(this.filePath, true);
        fw.write(task.formatForStorage());
        fw.write("\n");
        fw.close();
    }

    public boolean hasData() {
        File file = new File(this.filePath);
        return file.exists() && file.length() > 0;
    }

    public ArrayList<Task> loadTasks() throws FileNotFoundException {
        File f = new File(this.filePath);
        Scanner s = new Scanner(f);
        ArrayList<Task> tasks = new ArrayList<>();

        while (s.hasNext()) {
            String taskLine = s.nextLine();
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

        return tasks;
    }

    public void updateTaskStatus(int taskNum, Task task) throws IOException {
        Path filePath = Path.of(this.filePath);
        List<String> taskLines = Files.readAllLines(filePath);
        int lineIndex = taskNum - 1;
        taskLines.set(lineIndex, task.formatForStorage());
        Files.write(filePath, taskLines);
    }

    public void deleteTask(int taskNum) throws IOException {
        Path filePath = Path.of(this.filePath);
        List<String> taskLines = Files.readAllLines(filePath);
        int lineIndex = taskNum - 1;
        taskLines.remove(lineIndex);
        Files.write(filePath, taskLines);
    }
}
