import java.io.FileNotFoundException;
import java.io.IOException;
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

        Scanner s = new Scanner(System.in);

        String input = ""; // stores user input from scanner
        ArrayList<Task> tasks; // stores created tasks

        if (Storage.hasData()) {
            try {
                tasks = Storage.loadTasks();
            } catch (FileNotFoundException e) {
                System.out.println("    ----------------------------------------");
                System.out.println("    Error loading tasks");
                System.out.println("    ----------------------------------------");
                tasks = new ArrayList<>();
            }
        } else {
            tasks = new ArrayList<>();
        }

        while (true) {
            input = s.nextLine();


            String[] inputParts = input.split("\\s+", 2);

            Command command;

            switch (inputParts[0]) {
                case "bye" -> command = Command.BYE;
                case "list" -> command = Command.LIST;
                case "mark" -> command = Command.MARK;
                case "unmark" -> command = Command.UNMARK;
                case "todo" -> command = Command.TODO;
                case "deadline" -> command = Command.DEADLINE;
                case "event" -> command = Command.EVENT;
                case "delete" -> command = Command.DELETE;
                default -> command = Command.UNKNOWN;
            }

            // exit when bye command given
            if (command == Command.BYE) {
                break;
            } else if (command == Command.LIST) {
                System.out.println("    ----------------------------------------");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("    %d. %s\n", i + 1, tasks.get(i));
                }
                System.out.println("    ----------------------------------------");
            } else if (command == Command.MARK) {
                // mark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    task.mark();
                    Storage.updateTaskStatus(taskNum, task);
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
                } catch (IOException e) {
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Error in updating task in storage");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command == Command.UNMARK) {
                // unmark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    task.unmark();
                    Storage.updateTaskStatus(taskNum, task);
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
                } catch (IOException e) {
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Error in updating task in storage");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command == Command.TODO) {
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
                        try {
                            Storage.storeTask(newTask);
                            tasks.add(newTask);
                            System.out.println("    ----------------------------------------");
                            System.out.println("     Got it. I've added this task:");
                            System.out.printf("       %s\n", newTask);
                            System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                            System.out.println("    ----------------------------------------");
                        } catch (IOException e) {
                            System.out.println("    ----------------------------------------");
                            System.out.println("    Error when storing task");
                            System.out.println("    ----------------------------------------");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "todo" with no blank spaces
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Bro, cannot add empty todo");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command == Command.DEADLINE) {
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
                            try {
                                Storage.storeTask(newTask);
                                tasks.add(newTask);
                                System.out.println("    ----------------------------------------");
                                System.out.println("     Got it. I've added this task:");
                                System.out.printf("       %s\n", newTask);
                                System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                                System.out.println("    ----------------------------------------");
                            } catch (IOException e) {
                                System.out.println("    ----------------------------------------");
                                System.out.println("    Error when storing task");
                                System.out.println("    ----------------------------------------");
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "deadline" with no blank spaces
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Bro, cannot add empty deadline");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command == Command.EVENT) {
                try {
                    String content = inputParts[1];
                    if (content.isEmpty()) {
                        // catches if content is empty
                        // eg. user enters "event  " with trailing blank spaces
                        System.out.println("    ----------------------------------------");
                        System.out.println("    Bro, cannot add empty event");
                        System.out.println("    ----------------------------------------");
                    } else {
                        String[] contentParts = content.split("\\s+/from\\s+");
                        if (contentParts.length == 1) {
                            System.out.println("    ----------------------------------------");
                            System.out.println("    Missing or invalid /from command. Pls try again");
                            System.out.println("    ----------------------------------------");
                        } else {
                            String taskName = contentParts[0];
                            String[] timeParts = contentParts[1].split("\\s+/to\\s+");
                            if (timeParts.length == 1) {
                                System.out.println("    ----------------------------------------");
                                System.out.println("    Missing or invalid /to command. Pls try again");
                                System.out.println("    ----------------------------------------");
                            } else {
                                String from = timeParts[0];
                                String to = timeParts[1];
                                Task newTask = new Event(taskName, from, to);
                                try {
                                    Storage.storeTask(newTask);
                                    tasks.add(newTask);
                                    System.out.println("    ----------------------------------------");
                                    System.out.println("     Got it. I've added this task:");
                                    System.out.printf("       %s\n", newTask);
                                    System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                                    System.out.println("    ----------------------------------------");
                                } catch (IOException e) {
                                    System.out.println("    ----------------------------------------");
                                    System.out.println("    Error when storing task");
                                    System.out.println("    ----------------------------------------");
                                }
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "event" with no blank spaces
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Bro, cannot add empty event");
                    System.out.println("    ----------------------------------------");
                }
            } else if (command == Command.DELETE) {
                // unmark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    tasks.remove(taskNum - 1);
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Noted. I've removed this task:");
                    System.out.println("      " + task);
                    System.out.printf("    Now you have %d tasks in the list.\n", tasks.size());
                    System.out.println("    ----------------------------------------");
                } catch (ArrayIndexOutOfBoundsException e) {
                    // checks if a task number is provided
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Cannot delete empty task");
                    System.out.println("    ----------------------------------------");
                } catch (NumberFormatException e) {
                    // checks if delete command is followed by valid int i.e. 1 and not one
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Enter a valid task number. eg delete 1");
                    System.out.println("    ----------------------------------------");
                } catch (IndexOutOfBoundsException e) {
                    // checks if task number provided is within the number of tasks user actually has
                    System.out.println("    ----------------------------------------");
                    System.out.printf("     You only have %d task(s). Try again\n", tasks.size());
                    System.out.println("    ----------------------------------------");
                }
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
