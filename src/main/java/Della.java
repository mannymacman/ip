import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class Della {
    public static void main(String[] args) {

        UI.showWelcome();

        Scanner s = new Scanner(System.in);

        String input = ""; // stores user input from scanner
        ArrayList<Task> tasks; // stores created tasks

        if (Storage.hasData()) {
            try {
                tasks = Storage.loadTasks();
            } catch (FileNotFoundException e) {
                UI.showError("Unable to load tasks from storage");
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
                UI.showFarewell();
                break;
            } else if (command == Command.LIST) {
                UI.showTasks(tasks);
            } else if (command == Command.MARK) {
                // mark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    task.mark();
                    Storage.updateTaskStatus(taskNum, task);
                    UI.showMarkedTask(task);
                } catch (ArrayIndexOutOfBoundsException e) {
                    // checks if a task number is provided
                    UI.showError("Cannot mark empty task");
                } catch (NumberFormatException e) {
                    // checks if mark command is followed by valid int i.e. 1 and not one
                    UI.showError("Enter a valid task number. eg mark 1");
                } catch (IndexOutOfBoundsException e) {
                    // checks if task number provided is within the number of tasks user actually has
                    UI.showError(String.format("You only have %d task(s). Try again", tasks.size()));
                } catch (IOException e) {
                    UI.showError("Unable to update task in storage");
                }
            } else if (command == Command.UNMARK) {
                // unmark task
                try {
                    String content = inputParts[1];
                    int taskNum = Integer.parseInt(content);
                    Task task = tasks.get(taskNum - 1);
                    task.unmark();
                    Storage.updateTaskStatus(taskNum, task);
                    UI.showUnmarkedTask(task);
                } catch (ArrayIndexOutOfBoundsException e) {
                    // checks if a task number is provided
                    UI.showError("Cannot unmark empty task");
                } catch (NumberFormatException e) {
                    // checks if unmark command is followed by valid int i.e. 1 and not one
                    UI.showError("Enter a valid task number. eg unmark 1");
                } catch (IndexOutOfBoundsException e) {
                    // checks if task number provided is within the number of tasks user actually has
                    UI.showError(String.format("You only have %d task(s). Try again", tasks.size()));
                } catch (IOException e) {
                    UI.showError("Error in updating task in storage");
                }
            } else if (command == Command.TODO) {
                try {
                    String content = inputParts[1];
                    if (content.isEmpty()) {
                        // catches if content is empty
                        // eg. user enters "todo  " with trailing blank spaces
                        UI.showError("Bro, cannot add empty todo task");
                    } else {
                        Task newTask = new Todo(content);
                        try {
                            Storage.storeTask(newTask);
                            tasks.add(newTask);
                            UI.showAddedTask(newTask, tasks.size());
                        } catch (IOException e) {
                            UI.showError("Error when storing task");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "todo" with no blank spaces
                    UI.showError("Bro, cannot add empty todo task");
                }
            } else if (command == Command.DEADLINE) {
                try {
                    String content = inputParts[1];
                    if (content.isEmpty()) {
                        // catches if content is empty
                        // eg. user enters "deadline  " with trailing blank spaces
                        UI.showError("Bro, cannot add empty deadline");
                    } else {
                        String[] contentParts = content.split("\\s+/by\\s+");
                        // checks if user has entered valid /by command i.e. /by sunday
                        if (contentParts.length == 1) {
                            UI.showError("Missing or invalid /by command. Pls try again");
                        } else {
                            try {
                                String taskName = contentParts[0];
                                String dateString = contentParts[1];
                                LocalDateTime dateTime = DateParser.parseDateTime(dateString, "dd/MM/yyyy HHmm");
                                if (dateTime.isBefore(LocalDateTime.now())) {
                                    throw new IllegalArgumentException("DateTime cannot be before today");
                                }
                                Task newTask = new Deadline(taskName, dateTime);
                                Storage.storeTask(newTask);
                                tasks.add(newTask);
                                UI.showAddedTask(newTask, tasks.size());
                            } catch (DateTimeParseException e) {
                                UI.showError("Enter date in dd/MM/yyyy HHmm format");
                            } catch (IllegalArgumentException e) {
                                UI.showError("DateTime entered cannot be before today");
                            } catch (IOException e) {
                                UI.showError("Error when storing task");
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "deadline" with no blank spaces
                    UI.showError("Bro, cannot add empty deadline");
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
                                try {
                                    String fromDateString = timeParts[0];
                                    String toDateString = timeParts[1];
                                    LocalDateTime fromDateTime = DateParser.parseDateTime(fromDateString, "dd/MM/yyyy HHmm");
                                    LocalDateTime toDateTime = DateParser.parseDateTime(toDateString, "dd/MM/yyyy HHmm");
                                    if (fromDateTime.isBefore(LocalDateTime.now()) || fromDateTime.isBefore(LocalDateTime.now())) {
                                        throw new IllegalArgumentException("DateTime cannot be before today");
                                    }
                                    if (toDateTime.isBefore(fromDateTime)) {
                                        throw new InvalidEventDateException();
                                    }
                                    Task newTask = new Event(taskName, fromDateTime, toDateTime);
                                    Storage.storeTask(newTask);
                                    tasks.add(newTask);
                                    System.out.println("    ----------------------------------------");
                                    System.out.println("     Got it. I've added this task:");
                                    System.out.printf("       %s\n", newTask);
                                    System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                                    System.out.println("    ----------------------------------------");
                                } catch (DateTimeParseException e) {
                                    System.out.println("    ----------------------------------------");
                                    System.out.println("    Enter date in dd/MM/yyyy HHmm format");
                                    System.out.println("    ----------------------------------------");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("    ----------------------------------------");
                                    System.out.println("    DateTime entered cannot be before today");
                                    System.out.println("    ----------------------------------------");
                                } catch (InvalidEventDateException e) {
                                    System.out.println("    ----------------------------------------");
                                    System.out.println("    /to DateTime must be before /by DateTime");
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
                    Storage.deleteTask(taskNum);
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
                } catch (IOException e) {
                    System.out.println("    ----------------------------------------");
                    System.out.println("    Error in deleting task in storage");
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
    }
}
