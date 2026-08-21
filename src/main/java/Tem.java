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
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("added: " + command);
            }
            System.out.println(DIVIDER);
        }
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
