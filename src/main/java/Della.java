import java.util.*;

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

        Scanner s = new Scanner(System.in);

        String input = ""; // stores user input from scanner
        ArrayList<Task> tasks = new ArrayList<>(); // stores created tasks

        while (true) {
            input = s.nextLine();


            String[] inputParts = input.split("\\s+", 2);
            String command = inputParts[0];

            // exit when bye command given
            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                System.out.println("    ----------------------------------------");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("    %d. %s\n", i + 1, tasks.get(i));
                }
                System.out.println("    ----------------------------------------");
            } else if (command.equals("mark")) {
                // mark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    task.mark();
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Nice! I have marked this task as done:");
                    System.out.println("      marked: " + task);
                    System.out.println("    ----------------------------------------");
                } catch (ArrayIndexOutOfBoundsException e) {
                    // checks if a task number is provided
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Cannot mark empty task");
                    System.out.println("    ----------------------------------------");
                } catch (NumberFormatException e) {
                    // checks if mark command is followed by valid int i.e. 1 and not one
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Enter a valid task number. eg mark 1");
                    System.out.println("    ----------------------------------------");
                } catch (IndexOutOfBoundsException e) {
                    // checks if task number provided is within the number of tasks user actually has
                    System.out.println("    ----------------------------------------");
                    System.out.printf("     You only have %d task(s). Try again\n", tasks.size());
                    System.out.println("    ----------------------------------------");
                }
            } else if (command.equals("unmark")) {
                // unmark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    task.unmark();
                    System.out.println("    ----------------------------------------");
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println("      unmarked: " + task);
                    System.out.println("    ----------------------------------------");
                } catch (ArrayIndexOutOfBoundsException e) {
                    // checks if a task number is provided
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Cannot mark empty task");
                    System.out.println("    ----------------------------------------");
                } catch (NumberFormatException e) {
                    // checks if unmark command is followed by valid int i.e. 1 and not one
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Enter a valid task number. eg unmark 1");
                    System.out.println("    ----------------------------------------");
                } catch (IndexOutOfBoundsException e) {
                    // checks if task number provided is within the number of tasks user actually has
                    System.out.println("    ----------------------------------------");
                    System.out.printf("     You only have %d task(s). Try again\n", tasks.size());
                    System.out.println("    ----------------------------------------");
                }
            } else if (command.equals("todo")) {
                try {
                    String content = inputParts[1];
                    if (content.isEmpty()) {
                        // catches if content is empty
                        // eg. user enters "todo  " with trailing blank spaces
                        System.out.println("    ----------------------------------------");
                        System.out.println("    Bro, cannot add empty todo");
                        System.out.println("    ----------------------------------------");
                    } else {
                        Task newTask = new Todo(content);
                        tasks.add(newTask);
                        System.out.println("    ----------------------------------------");
                        System.out.println("     Got it. I've added this task:");
                        System.out.printf("       %s\n", newTask);
                        System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                        System.out.println("    ----------------------------------------");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "todo" with no blank spaces
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Bro, cannot add empty todo");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command.equals("deadline")) {
                try {
                    String content = inputParts[1];
                    if (content.isEmpty()) {
                        // catches if content is empty
                        // eg. user enters "deadline  " with trailing blank spaces
                        System.out.println("    ----------------------------------------");
                        System.out.println("    Bro, cannot add empty deadline");
                        System.out.println("    ----------------------------------------");
                    } else {
                        String[] contentParts = content.split("\\s+/by\\s+");
                        // checks if user has entered valid /by command i.e. /by sunday
                        if (contentParts.length == 1) {
                            System.out.println("    ----------------------------------------");
                            System.out.println("    Missing or invalid /by command. Pls try again");
                            System.out.println("    ----------------------------------------");
                        } else {
                            Task newTask = new Deadline(contentParts[0], contentParts[1]);
                            tasks.add(newTask);
                            System.out.println("    ----------------------------------------");
                            System.out.println("     Got it. I've added this task:");
                            System.out.printf("       %s\n", newTask);
                            System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                            System.out.println("    ----------------------------------------");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "deadline" with no blank spaces
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Bro, cannot add empty deadline");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command.equals("event")) {
                String content = inputParts[1];
                String[] contentParts = content.split(" /from ");
                String taskName = contentParts[0];
                String[] timeParts = contentParts[1].split(" /to ");
                String from = timeParts[0];
                String to = timeParts[1];
                Task newTask = new Event(taskName, from, to);
                tasks.add(newTask);
                System.out.println("    ----------------------------------------");
                System.out.println("     Got it. I've added this task:");
                System.out.printf("       %s\n", newTask);
                System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                System.out.println("    ----------------------------------------");
            } else {
                // handles invalid and empty commands
                System.out.println("    ----------------------------------------");
                System.out.println("    I don't recognise this command :( Try again pls tyvm");
                System.out.println("    ----------------------------------------");
            }
        }
        s.close();
        System.out.println("========================================");
        System.out.println(farewell);
    }
}
