import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    public static void storeTask(Task task) throws IOException {
        FileWriter fw = new FileWriter("./data/della.txt");
        fw.write(task.toString());
        fw.close();
    }
}
