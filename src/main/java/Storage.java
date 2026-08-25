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
    public static void storeTask(Task task) throws IOException {
        FileWriter fw = new FileWriter("./data/della.txt", true);
        fw.write(task.formatForStorage());
        fw.write("\n");
        fw.close();
    }

    public static boolean hasData() {
        File file = new File("./data/della.txt");
        return file.exists() && file.length() > 0;
    }

    public static ArrayList<Task> loadTasks() throws FileNotFoundException {
        File f = new File("./data/della.txt");
        Scanner s = new Scanner(f);
        ArrayList<Task> tasks = new ArrayList<>();

        while (s.hasNext()) {
            String taskLine = s.nextLine();
            String[] taskParts = taskLine.split("\\|");
            if (taskParts[0].equals("T")) {
                tasks.add(new Todo(taskParts[2], taskParts[1].equals("1")));
            } else if (taskParts[0].equals("D")) {
                tasks.add(new Deadline(taskParts[2], taskParts[1].equals("1"), taskParts[3]));
            } else {
                tasks.add(new Event(taskParts[2], taskParts[1].equals("1"), taskParts[3], taskParts[4]));
            }
        }

        return tasks;
    }

    public static void updateTaskStatus(int taskNum, Task task) throws IOException {
        Path filePath = Path.of("./data/della.txt");
        List<String> taskLines = Files.readAllLines(filePath);
        int lineIndex = taskNum - 1;
        taskLines.set(lineIndex, task.formatForStorage());
        Files.write(filePath, taskLines);
    }
}
