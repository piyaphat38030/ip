package tem;

import java.util.List;
import java.util.Scanner;

/**
 * Handles printing messages to the user and reading commands from standard input.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows the welcome banner and greeting.
     */
    public void showWelcome() {
        String banner = " _____\n"
                + "|_   _|__ _ __ ___\n"
                + "  | |/ _ \\ '_ ` _ \\\n"
                + "  | |  __/ | | | | |\n"
                + "  |_|\\___|_| |_| |_|\n";
        showLine();
        System.out.println(banner);
        System.out.println("Hello! I'm Tem.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Shows the standard divider line.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return trimmed command text, or {@code null} when input has ended
     */
    public String readCommand() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    /**
     * Shows an error that occurred while loading saved tasks.
     *
     * @param message explanation of the loading problem
     */
    public void showLoadingError(String message) {
        System.out.println("Error: " + message);
        System.out.println("Starting with an empty task list.");
        showLine();
    }

    /**
     * Shows an error message for an invalid command or action.
     *
     * @param message explanation of the error
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Shows the farewell message when the user exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Shows confirmation after a task is added.
     *
     * @param task task that was added
     * @param taskCount number of tasks now in the list
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Shows confirmation after a task is marked as done.
     *
     * @param task task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Shows confirmation after a task is marked as not done.
     *
     * @param task task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Shows confirmation after a task is deleted.
     *
     * @param task task that was removed
     * @param taskCount number of tasks remaining in the list
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Shows every task in the list with its one-based position.
     *
     * @param tasks tasks currently stored by Tem
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Shows tasks whose descriptions match the find keyword.
     *
     * @param tasks tasks currently stored by Tem
     * @param matchingIndices zero-based indices of tasks to display
     */
    public void showMatchingTasks(TaskList tasks, List<Integer> matchingIndices) {
        if (matchingIndices.isEmpty()) {
            System.out.println("No matching tasks in your list.");
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int index : matchingIndices) {
            System.out.println((index + 1) + "." + tasks.get(index));
        }
    }
}
