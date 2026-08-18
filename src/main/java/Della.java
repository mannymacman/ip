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
        ArrayList<Task> tasks = new ArrayList<>();

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
            } else {
                String[] commandParts = command.trim().split(" ");

                if (commandParts.length == 2 && commandParts[0].equals("mark")) {
                    // mark task
                    try {
                        int taskNum = Integer.parseInt(commandParts[1]);

                        // check if task is within the number of tasks provided
                        if (taskNum > 0 && taskNum <= tasks.size()) {
                            Task task = tasks.get(taskNum - 1);
                            task.mark();
                            System.out.println("    ----------------------------------------");
                            System.out.println("    Nice! I have marked this task as done:");
                            System.out.println("      marked: " + task);
                            System.out.println("    ----------------------------------------");
                        } else {
                            System.out.println("    ----------------------------------------");
                            System.out.println("    Invalid task provided. Please try again!");
                            System.out.println("    ----------------------------------------");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("    ----------------------------------------");
                        System.out.println("    Invalid task provided. Please try again!");
                        System.out.println("    ----------------------------------------");
                    }
                } else if (commandParts.length == 2 && commandParts[0].equals("unmark")) {
                    // unmark task
                    try {
                        int taskNum = Integer.parseInt(commandParts[1]);

                        // check if task is within the number of tasks provided
                        if (taskNum > 0 && taskNum <= tasks.size()) {
                            Task task = tasks.get(taskNum - 1);
                            task.unmark();
                            System.out.println("    ----------------------------------------");
                            System.out.println("    OK, I've marked this task as not done yet:");
                            System.out.println("      unmarked: " + task);
                            System.out.println("    ----------------------------------------");
                        } else {
                            System.out.println("    ----------------------------------------");
                            System.out.println("    Invalid task provided. Please try again!");
                            System.out.println("    ----------------------------------------");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("    ----------------------------------------");
                        System.out.println("    Invalid task provided. Please try again!");
                        System.out.println("    ----------------------------------------");
                    }
                } else { // if just adding tasks
                    Task newTask = new Task(command);
                    tasks.add(newTask);
                    System.out.println("    ----------------------------------------");
                    System.out.printf("    added: %s\n", newTask);
                    System.out.println("    ----------------------------------------");
                }
            }
        }
        s.close();
        System.out.println("========================================");
        System.out.println(farewell);
    }
}
