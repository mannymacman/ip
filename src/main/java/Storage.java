import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    public static void storeTask(Task task) {
        try {
            FileWriter fw = new FileWriter("./data/della.txt");
            fw.write(task.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error when storing task!");
        }
    }
}
