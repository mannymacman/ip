import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    public static void storeTask(Task task) throws IOException {
        FileWriter fw = new FileWriter("./data/della.txt", true);
        fw.write(task.toString());
        fw.write("\n");
        fw.close();
    }
}
