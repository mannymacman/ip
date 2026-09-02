package della;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import della.command.Command;
import della.parser.Parser;
import della.storage.Storage;
import della.task.Task;
import della.task.TaskList;
import della.ui.UI;

/**
 * Runs the Della task-management application.
 */
public class Della {
    private final Storage storage;
    private TaskList taskList;

    /**
     * Creates Della using the specified file to persist tasks.
     *
     * @param filePath Path of the task storage file.
     */
    public Della(String filePath) {
        this.storage = new Storage(filePath);

        if (this.storage.hasData()) {
            try {
                this.taskList = new TaskList(storage.loadTasks());
            } catch (FileNotFoundException e) {
                System.out.println(UI.showError("Unable to load tasks from storage"));
                this.taskList = new TaskList();
            }
        } else {
            this.taskList = new TaskList();
        }
    }

    /**
     * Runs the command-reading loop until the user enters the bye command.
     */
    public void run() {
        System.out.println(UI.showWelcome());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            System.out.println(getResponse(input));
            if (Parser.parseCommand(input) == Command.BYE) {
                break;
            }
        }
        System.out.println(UI.showFarewell());
        scanner.close();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input User input.
     */
    public String getResponse(String input) {

        Command command = Parser.parseCommand(input);

        if (command == Command.BYE) {
            return UI.showFarewell();
        } else if (command == Command.LIST) {
            return UI.showTasks(taskList.getTasks());
        } else if (command == Command.MARK) {
            try {
                String argument = Parser.parseArguments(input);
                int taskNum = Parser.parseTaskNumber(argument);
                Task task = taskList.mark(taskNum - 1);
                storage.updateTaskStatus(taskNum, task);
                return UI.showMarkedTask(task);
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Cannot mark empty task");
            } catch (NumberFormatException e) {
                return UI.showError("Enter a valid task number. eg mark 1");
            } catch (IndexOutOfBoundsException e) {
                return UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
            } catch (IOException e) {
                return UI.showError("Unable to update task in storage");
            }
        } else if (command == Command.UNMARK) {
            try {
                String argument = Parser.parseArguments(input);
                int taskNum = Parser.parseTaskNumber(argument);
                Task task = taskList.unmark(taskNum - 1);
                storage.updateTaskStatus(taskNum, task);
                return UI.showUnmarkedTask(task);
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Cannot unmark empty task");
            } catch (NumberFormatException e) {
                return UI.showError("Enter a valid task number. eg unmark 1");
            } catch (IndexOutOfBoundsException e) {
                return UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
            } catch (IOException e) {
                return UI.showError("Error in updating task in storage");
            }
        } else if (command == Command.TODO) {
            try {
                String argument = Parser.parseArguments(input);
                Task newTask = Parser.parseTodo(argument);
                storage.storeTask(newTask);
                taskList.add(newTask);
                return UI.showAddedTask(newTask, taskList.size());
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Bro, cannot add empty todo task");
            } catch (IllegalArgumentException e) {
                return UI.showError(e.getMessage());
            } catch (IOException e) {
                return UI.showError("Error when storing task");
            }
        } else if (command == Command.DEADLINE) {
            try {
                String argument = Parser.parseArguments(input);
                Task newTask = Parser.parseDeadline(argument);
                storage.storeTask(newTask);
                taskList.add(newTask);
                return UI.showAddedTask(newTask, taskList.size());
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Bro, cannot add empty deadline");
            } catch (DateTimeParseException e) {
                return UI.showError("Enter date in dd/MM/yyyy HHmm format");
            } catch (IllegalArgumentException e) {
                return UI.showError(e.getMessage());
            } catch (IOException e) {
                return UI.showError("Error when storing task");
            }
        } else if (command == Command.EVENT) {
            try {
                String argument = Parser.parseArguments(input);
                Task newTask = Parser.parseEvent(argument);
                storage.storeTask(newTask);
                taskList.add(newTask);
                return UI.showAddedTask(newTask, taskList.size());
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Bro, cannot add empty event");
            } catch (DateTimeParseException e) {
                return UI.showError("Enter date in dd/MM/yyyy HHmm format");
            } catch (IllegalArgumentException e) {
                return UI.showError(e.getMessage());
            } catch (IOException e) {
                return UI.showError("Error when storing task");
            }
        } else if (command == Command.DELETE) {
            try {
                String argument = Parser.parseArguments(input);
                int taskNum = Parser.parseTaskNumber(argument);
                Task task = taskList.delete(taskNum - 1);
                storage.deleteTask(taskNum);
                return UI.showDeletedTask(task, taskList.size());
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Cannot delete empty task");
            } catch (NumberFormatException e) {
                return UI.showError("Enter a valid task number. eg delete 1");
            } catch (IndexOutOfBoundsException e) {
                return UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
            } catch (IOException e) {
                return UI.showError("Error in deleting task in storage");
            }
        } else if (command == Command.FIND) {
            try {
                String argument = Parser.parseArguments(input);
                ArrayList<Task> searchResult = Parser.parseFindTask(argument, taskList.getTasks());
                return UI.showTasks(searchResult);
            } catch (ArrayIndexOutOfBoundsException e) {
                return UI.showError("Bro, cannot find nothing");
            } catch (IllegalArgumentException e) {
                return UI.showError(e.getMessage());
            }
        } else {
            return UI.showError("I don't recognise this command :( Try again pls tyvm");
        }
    }

    /**
     * Starts the Della application.
     *
     * @param args Command-line arguments, which are currently ignored.
     */
    public static void main(String[] args) {
        new Della("./data/della.txt").run();
    }
}
