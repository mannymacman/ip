import java.util.ArrayList;

public class UI {
    public static void showWelcome() {
        String banner =
                "   DDDD   eeee  l      l       aaaa\n" +
                        "   D   D  e     l      l      a    a\n" +
                        "   D   D  eee   l      l      aaaaaa\n" +
                        "   D   D  e     l      l      a    a\n" +
                        "   DDDD   eeee  llll   llll   a    a";
        String greeting = "Hi! I'm Della :))\nHow can I help you?";

        System.out.println(banner);
        System.out.println("========================================");
        System.out.println(greeting);
        System.out.println("========================================");
    }

    public static void showFarewell() {
        String farewell = "Byee! Rest well!";
        System.out.println("========================================");
        System.out.println(farewell);
    }

    public static void showError(String errorMsg) {
        System.out.println("    ----------------------------------------");
        System.out.printf("    %s\n", errorMsg);
        System.out.println("    ----------------------------------------");
    }

    public static void showTasks(ArrayList<Task> taskList) {
        System.out.println("    ----------------------------------------");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("    %d. %s\n", i + 1, taskList.get(i));
        }
        System.out.println("    ----------------------------------------");
    }

    public static void showMarkedTask(Task task) {
        System.out.println("    ----------------------------------------");
        System.out.println("    Nice! I have marked this task as done:");
        System.out.println("      marked: " + task);
        System.out.println("    ----------------------------------------");
    }
}
