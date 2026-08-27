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
                UI.showError("Unable to load tasks from storage");
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
        UI.showWelcome();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            Command command = Parser.parseCommand(input);

            if (command == Command.BYE) {
                UI.showFarewell();
                break;
            } else if (command == Command.LIST) {
                UI.showTasks(taskList.getTasks());
            } else if (command == Command.MARK) {
                try {
                    String argument = Parser.parseArguments(input);
                    int taskNum = Parser.parseTaskNumber(argument);
                    Task task = taskList.mark(taskNum - 1);
                    storage.updateTaskStatus(taskNum, task);
                    UI.showMarkedTask(task);
                } catch (ArrayIndexOutOfBoundsException e) {
                    UI.showError("Cannot mark empty task");
                } catch (NumberFormatException e) {
                    UI.showError("Enter a valid task number. eg mark 1");
                } catch (IndexOutOfBoundsException e) {
                    UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
                } catch (IOException e) {
                    UI.showError("Unable to update task in storage");
                }
            } else if (command == Command.UNMARK) {
                try {
                    String argument = Parser.parseArguments(input);
                    int taskNum = Parser.parseTaskNumber(argument);
                    Task task = taskList.unmark(taskNum - 1);
                    storage.updateTaskStatus(taskNum, task);
                    UI.showUnmarkedTask(task);
                } catch (ArrayIndexOutOfBoundsException e) {
                    UI.showError("Cannot unmark empty task");
                } catch (NumberFormatException e) {
                    UI.showError("Enter a valid task number. eg unmark 1");
                } catch (IndexOutOfBoundsException e) {
                    UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
                } catch (IOException e) {
                    UI.showError("Error in updating task in storage");
                }
            } else if (command == Command.TODO) {
                try {
                    String argument = Parser.parseArguments(input);
                    Task newTask = Parser.parseTodo(argument);
                    storage.storeTask(newTask);
                    taskList.add(newTask);
                    UI.showAddedTask(newTask, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
                    UI.showError("Bro, cannot add empty todo task");
                } catch (IllegalArgumentException e) {
                    UI.showError(e.getMessage());
                } catch (IOException e) {
                    UI.showError("Error when storing task");
                }
            } else if (command == Command.DEADLINE) {
                try {
                    String argument = Parser.parseArguments(input);
                    Task newTask = Parser.parseDeadline(argument);
                    storage.storeTask(newTask);
                    taskList.add(newTask);
                    UI.showAddedTask(newTask, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
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
                    String argument = Parser.parseArguments(input);
                    Task newTask = Parser.parseEvent(argument);
                    storage.storeTask(newTask);
                    taskList.add(newTask);
                    UI.showAddedTask(newTask, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
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
                    String argument = Parser.parseArguments(input);
                    int taskNum = Parser.parseTaskNumber(argument);
                    Task task = taskList.delete(taskNum - 1);
                    storage.deleteTask(taskNum);
                    UI.showDeletedTask(task, taskList.size());
                } catch (ArrayIndexOutOfBoundsException e) {
                    UI.showError("Cannot delete empty task");
                } catch (NumberFormatException e) {
                    UI.showError("Enter a valid task number. eg delete 1");
                } catch (IndexOutOfBoundsException e) {
                    UI.showError(String.format("You only have %d task(s). Try again", taskList.size()));
                } catch (IOException e) {
                    UI.showError("Error in deleting task in storage");
                }
            } else if (command == Command.FIND) {
                try {
                    String argument = Parser.parseArguments(input);
                    ArrayList<Task> searchResult = Parser.parseFindTask(argument, taskList.getTasks());
                    UI.showTasks(searchResult);
                } catch (ArrayIndexOutOfBoundsException e) {
                    UI.showError("Bro, cannot find nothing");
                } catch (IllegalArgumentException e) {
                    UI.showError(e.getMessage());
                }
            } else {
                UI.showError("I don't recognise this command :( Try again pls tyvm");
            }
        }
        scanner.close();
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
