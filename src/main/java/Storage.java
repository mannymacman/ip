import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    public static void storeTask(Task task) throws IOException {
        FileWriter fw = new FileWriter("./data/della.txt", true);
        fw.write(task.formatForStorage());
        fw.write("\n");
        fw.close();
    }
}
