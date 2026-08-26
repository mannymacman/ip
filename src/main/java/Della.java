import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Della {
    public static void main(String[] args) {

        UI.showWelcome();

        Scanner s = new Scanner(System.in);

        String input = ""; // stores user input from scanner
        TaskList taskList; // manage tasklist

        if (Storage.hasData()) {
            try {
                taskList = new TaskList(Storage.loadTasks());
            } catch (FileNotFoundException e) {
                UI.showError("Unable to load tasks from storage");
                taskList = new TaskList();
            }
        } else {
            taskList = new TaskList();
        }

        while (true) {
            input = s.nextLine();

            Command command = Parser.parseCommand(input);
            String argument;

            // exit when bye command given
            if (command == Command.BYE) {
                UI.showFarewell();
                break;
            } else if (command == Command.LIST) {
                UI.showTasks(taskList.getTasks());
            } else if (command == Command.MARK) {
                // mark task
                try {
                    argument = Parser.parseArguments(input);
                    int taskNum = Parser.parseTaskNumber(argument);
                    Task task = taskList.mark(taskNum - 1);
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
                    UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
                } catch (IOException e) {
                    UI.showError("Unable to update task in storage");
                }
            } else if (command == Command.UNMARK) {
                // unmark task
                try {
                    argument = Parser.parseArguments(input);
                    int taskNum = Parser.parseTaskNumber(argument);
                    Task task = taskList.unmark(taskNum - 1);
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
                    UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
                } catch (IOException e) {
                    UI.showError("Error in updating task in storage");
                }
            } else if (command == Command.TODO) {
                try {
                    argument = Parser.parseArguments(input);
                    Task newTask = Parser.parseTodo(argument);
                    Storage.storeTask(newTask);
                    taskList.add(newTask);
                    UI.showAddedTask(newTask, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "todo" with no blank spaces
                    UI.showError("Bro, cannot add empty todo task");
                } catch (IllegalArgumentException e) {
                    UI.showError(e.getMessage());
                } catch (IOException e) {
                    UI.showError("Error when storing task");
                }
            } else if (command == Command.DEADLINE) {
                try {
                    argument = Parser.parseArguments(input);
                    Task newTask = Parser.parseDeadline(argument);
                    Storage.storeTask(newTask);
                    taskList.add(newTask);
                    UI.showAddedTask(newTask, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "deadline" with no blank spaces
                    UI.showError("Bro, cannot add empty deadline");
                } catch (DateTimeParseException e) {
                    UI.showError("Enter date in dd/MM/yyyy HHmm format");
                } catch (IllegalArgumentException e) {
                    UI.showError(e.getMessage());
                } catch (IOException e) {
                    UI.showError("Error when storing task");
                }
            } else if (command == Command.EVENT) {
                try {
                    argument = Parser.parseArguments(input);
                    Task newTask = Parser.parseEvent(argument);
                    Storage.storeTask(newTask);
                    taskList.add(newTask);
                    UI.showAddedTask(newTask, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
                    // catches if content is empty
                    // eg. user enters "event" with no blank spaces
                    UI.showError("Bro, cannot add empty event");
                } catch (DateTimeParseException e) {
                    UI.showError("Enter date in dd/MM/yyyy HHmm format");
                } catch (IllegalArgumentException e) {
                    UI.showError(e.getMessage());
                } catch (IOException e) {
                    UI.showError("Error when storing task");
                }
            } else if (command == Command.DELETE) {
                try {
                    argument = Parser.parseArguments(input);
                    int taskNum = Parser.parseTaskNumber(argument);
                    Task task = taskList.delete(taskNum - 1);
                    Storage.deleteTask(taskNum);
                    UI.showDeletedTask(task, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
                    // checks if a task number is provided
                    UI.showError("Cannot delete empty task");
                } catch (NumberFormatException e) {
                    // checks if delete command is followed by valid int i.e. 1 and not one
                    UI.showError("Enter a valid task number. eg delete 1");
                } catch (IndexOutOfBoundsException e) {
                    // checks if task number provided is within the number of tasks user actually has
                    UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
                } catch (IOException e) {
                    UI.showError("Error in deleting task in storage");
                }
            } else {
                // handles invalid and empty commands
                UI.showError("I don't recognise this command :( Try again pls tyvm");
            }
        }
        s.close();
    }
}
