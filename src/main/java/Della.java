import java.util.Scanner;
import java.util.ArrayList;

public class Della {
    public static void main(String[] args) {
        String banner =
                "   DDDD   eeee  l      l       aaaa\n" +
                "   D   D  e     l      l      a    a\n" +
                "   D   D  eee   l      l      aaaaaa\n" +
                "   D   D  e     l      l      a    a\n" +
                "   DDDD   eeee  llll   llll   a    a";
        String greeting = "Hi! I'm Della :))\nHow can I help you?";
        String farewell = "Byee! Rest well!";

        System.out.println(banner);
        System.out.println("========================================");
        System.out.println(greeting);
        System.out.println("========================================");

        String command = "";
        Scanner s = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            command = s.nextLine();

            // exit when bye command given
            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                System.out.println("    ----------------------------------------");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("    %d. %s\n", i + 1, tasks.get(i));
                }
                System.out.println("    ----------------------------------------");
            } else { // if just adding tasks
                tasks.add(command);
                System.out.println("    ----------------------------------------");
                System.out.printf("    added: %s\n", command);
                System.out.println("    ----------------------------------------");
            }
        }
        s.close();
        System.out.println("========================================");
        System.out.println(farewell);
    }
}
