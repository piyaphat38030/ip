import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple command-line assistant that stores tasks until asked to exit.
 */
public class Tem {
    private static final String DIVIDER = "____________________________________________________________";
    private static final Storage STORAGE = new Storage("data", "tem.txt");

    /**
     * Starts Tem and processes commands entered through standard input.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = " _____\n"
                + "|_   _|__ _ __ ___\n"
                + "  | |/ _ \\ '_ ` _ \\\n"
                + "  | |  __/ | | | | |\n"
                + "  |_|\\___|_| |_| |_|\n";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm Tem.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);

        List<Task> tasks = loadTasks();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER);
                break;
            }

            try {
                if (command.equals("list")) {
                    printTaskList(tasks);
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    Task task = tasks.get(getTaskIndex(command, tasks, "mark as done"));
                    task.markAsDone();
                    saveTasks(tasks);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    Task task = tasks.get(getTaskIndex(command, tasks, "mark as not done"));
                    task.unmarkAsDone();
                    saveTasks(tasks);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    Task deletedTask = tasks.remove(getTaskIndex(command, tasks, "delete"));
                    saveTasks(tasks);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + deletedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    Task task = createTask(command);
                    tasks.add(task);
                    saveTasks(tasks);
                    printAddedTask(task, tasks.size());
                }
            } catch (TemException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Loads saved tasks, or starts with an empty list when none exist yet.
     *
     * @return tasks restored from disk
     */
    private static List<Task> loadTasks() {
        try {
            return STORAGE.load();
        } catch (TemException exception) {
            System.out.println("Error: " + exception.getMessage());
            System.out.println("Starting with an empty task list.");
            System.out.println(DIVIDER);
            return new ArrayList<>();
        }
    }

    /**
     * Writes the current task list to disk after a change.
     *
     * @param tasks tasks currently stored by Tem
     * @throws TemException if saving fails
     */
    private static void saveTasks(List<Task> tasks) throws TemException {
        STORAGE.save(tasks);
    }

    /**
     * Creates a task from a task-creation command.
     *
     * @param command command entered by the user
     * @return the task described by the command
     * @throws TemException if the command is unknown or required details are missing
     */
    private static Task createTask(String command) throws TemException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring("todo".length()).trim();
            ensurePresent(description, "A todo needs a description. Try: todo read a book");
            return new Todo(description);
        }
        if (command.equals("deadline") || command.startsWith("deadline ")) {
            return createDeadline(command.substring("deadline".length()).trim());
        }
        if (command.equals("event") || command.startsWith("event ")) {
            return createEvent(command.substring("event".length()).trim());
        }
        if (command.isEmpty()) {
            throw new TemException("Please enter a command.");
        }
        throw new TemException("I don't recognise that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
    }

    /**
     * Creates a deadline from text in the form {@code description /by yyyy-MM-dd}.
     *
     * @param details deadline description and due date
     * @return the created deadline
     * @throws TemException if the description or due date is missing or invalid
     */
    private static Task createDeadline(String details) throws TemException {
        int byIndex = details.indexOf(" /by ");
        if (details.startsWith("/by ")) {
            byIndex = 0;
        }
        if (byIndex < 0) {
            throw new TemException("A deadline needs a due date. Try: deadline return book /by 2019-10-15");
        }
        String description = details.substring(0, byIndex).trim();
        int byValueStart = byIndex == 0 ? "/by ".length() : byIndex + " /by ".length();
        String byText = details.substring(byValueStart).trim();
        ensurePresent(description, "A deadline needs a description before /by.");
        ensurePresent(byText, "A deadline needs a due date after /by.");
        return new Deadline(description, parseDate(byText));
    }

    /**
     * Parses a date written as {@code yyyy-MM-dd}.
     *
     * @param dateText date text supplied by the user
     * @return parsed date
     * @throws TemException if {@code dateText} is not a valid {@code yyyy-MM-dd} date
     */
    private static LocalDate parseDate(String dateText) throws TemException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new TemException("Use a date like 2019-10-15 after /by.");
        }
    }

    /**
     * Creates an event from text in the form {@code description /from start /to end}.
     *
     * @param details event description, start time, and end time
     * @return the created event
     * @throws TemException if the description, start time, or end time is missing
     */
    private static Task createEvent(String details) throws TemException {
        int fromIndex = details.indexOf("/from ");
        int toIndex = details.indexOf("/to ");
        if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
            throw new TemException("An event needs /from and /to times. Try: event meeting /from Mon 2pm /to 4pm");
        }
        String description = details.substring(0, fromIndex).trim();
        String from = details.substring(fromIndex + "/from ".length(), toIndex).trim();
        String to = details.substring(toIndex + "/to ".length()).trim();
        ensurePresent(description, "An event needs a description before /from.");
        ensurePresent(from, "An event needs a start time after /from.");
        ensurePresent(to, "An event needs an end time after /to.");
        return new Event(description, from, to);
    }

    /**
     * Prints the confirmation shown after adding a task.
     *
     * @param task task that was added
     * @param taskCount number of tasks now in the list
     */
    private static void printAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints every task together with its one-based position in the task list.
     *
     * @param tasks tasks currently stored by Tem
     */
    private static void printTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Finds the zero-based list position referenced by a numbered task command.
     *
     * @param command command containing a one-based task number
     * @param tasks tasks currently stored by Tem
     * @param action action described in a missing-number error message
     * @return zero-based position of the selected task
     * @throws TemException if the task number is missing, malformed, or out of range
     */
    private static int getTaskIndex(String command, List<Task> tasks, String action) throws TemException {
        int firstSpaceIndex = command.indexOf(' ');
        String taskNumberText = firstSpaceIndex < 0 ? "" : command.substring(firstSpaceIndex + 1).trim();
        if (taskNumberText.isEmpty()) {
            throw new TemException("Please provide the task number to " + action + ".");
        }
        try {
            int taskNumber = Integer.parseInt(taskNumberText);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new TemException("Choose a task number from 1 to " + tasks.size() + ".");
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new TemException("The task number must be a whole number.");
        }
    }

    /**
     * Rejects a required command field that contains no visible text.
     *
     * @param value field value supplied by the user
     * @param message explanation of the missing field
     * @throws TemException if {@code value} is empty
     */
    private static void ensurePresent(String value, String message) throws TemException {
        if (value.isEmpty()) {
            throw new TemException(message);
        }
    }
}
