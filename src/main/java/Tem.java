import java.util.Scanner;

/**
 * A simple command-line assistant that stores tasks until asked to exit.
 */
public class Tem {
    private static final String DIVIDER = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    /**
     * Starts Tem and processes commands entered through standard input.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = " _____                 \n"
                + "|_   _|__ _ __ ___     \n"
                + "  | |/ _ \\ '_ ` _ \\    \n"
                + "  | |  __/ | | | | |   \n"
                + "  |_|\\___|_| |_| |_|   \n";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm Tem.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER);
                break;
            }

            if (command.equals("list")) {
                printTaskList(tasks, taskCount);
            } else if (command.startsWith("mark ")) {
                Task task = getTask(command, tasks, taskCount);
                if (task != null) {
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                }
            } else if (command.startsWith("unmark ")) {
                Task task = getTask(command, tasks, taskCount);
                if (task != null) {
                    task.unmarkAsDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                }
            } else {
                Task task = createTask(command);
                if (task != null) {
                    tasks[taskCount] = task;
                    taskCount++;
                    printAddedTask(task, taskCount);
                }
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Creates a task from a task-creation command.
     *
     * @param command command entered by the user
     * @return the task described by the command, or {@code null} for invalid input
     */
    private static Task createTask(String command) {
        if (command.startsWith("todo ")) {
            return new Todo(command.substring("todo ".length()));
        }
        if (command.startsWith("deadline ")) {
            return createDeadline(command.substring("deadline ".length()));
        }
        if (command.startsWith("event ")) {
            return createEvent(command.substring("event ".length()));
        }
        System.out.println("I don't understand that command.");
        return null;
    }

    /**
     * Creates a deadline from text in the form {@code description /by time}.
     *
     * @param details deadline description and due time
     * @return the created deadline, or {@code null} if the due time is missing
     */
    private static Task createDeadline(String details) {
        int byIndex = details.indexOf(" /by ");
        if (byIndex < 0) {
            System.out.println("Use: deadline DESCRIPTION /by TIME");
            return null;
        }
        String description = details.substring(0, byIndex);
        String by = details.substring(byIndex + " /by ".length());
        return new Deadline(description, by);
    }

    /**
     * Creates an event from text in the form {@code description /from start /to end}.
     *
     * @param details event description, start time, and end time
     * @return the created event, or {@code null} if either time is missing
     */
    private static Task createEvent(String details) {
        int fromIndex = details.indexOf(" /from ");
        int toIndex = details.indexOf(" /to ");
        if (fromIndex < 0 || toIndex < 0 || toIndex < fromIndex) {
            System.out.println("Use: event DESCRIPTION /from START /to END");
            return null;
        }
        String description = details.substring(0, fromIndex);
        String from = details.substring(fromIndex + " /from ".length(), toIndex);
        String to = details.substring(toIndex + " /to ".length());
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
     * @param taskCount number of stored tasks
     */
    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    /**
     * Finds the task referenced by a mark or unmark command.
     *
     * @param command command containing a one-based task number
     * @param tasks tasks currently stored by Tem
     * @param taskCount number of stored tasks
     * @return the selected task, or {@code null} when the task number is invalid
     */
    private static Task getTask(String command, Task[] tasks, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring(command.indexOf(' ') + 1));
            if (taskNumber < 1 || taskNumber > taskCount) {
                System.out.println("Please provide a task number from 1 to " + taskCount + ".");
                return null;
            }
            return tasks[taskNumber - 1];
        } catch (NumberFormatException exception) {
            System.out.println("Please provide a valid task number.");
            return null;
        }
    }
}
