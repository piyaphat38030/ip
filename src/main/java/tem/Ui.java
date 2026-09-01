package tem;

import java.util.List;
import java.util.Scanner;

/**
 * Builds user-facing text and reads commands from standard input for the CLI.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String BANNER = " _____\n"
            + "|_   _|__ _ __ ___\n"
            + "  | |/ _ \\ '_ ` _ \\\n"
            + "  | |  __/ | | | | |\n"
            + "  |_|\\___|_| |_| |_|\n";

    private final Scanner scanner;

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the welcome banner and greeting shown at startup.
     *
     * @return welcome text without divider lines
     */
    public String welcomeMessage() {
        return BANNER + "\nHello! I'm Tem.\nWhat can I do for you?";
    }

    /**
     * Shows the welcome banner and greeting.
     */
    public void showWelcome() {
        showLine();
        System.out.println(welcomeMessage());
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
     * Returns text explaining a loading failure.
     *
     * @param message explanation of the loading problem
     * @return loading error message
     */
    public String loadingErrorMessage(String message) {
        return "Error: " + message + "\nStarting with an empty task list.";
    }

    /**
     * Shows an error that occurred while loading saved tasks.
     *
     * @param message explanation of the loading problem
     */
    public void showLoadingError(String message) {
        System.out.println(loadingErrorMessage(message));
        showLine();
    }

    /**
     * Shows an error message for an invalid command or action.
     *
     * @param message explanation of the error
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Returns the farewell message when the user exits.
     *
     * @return goodbye text
     */
    public String goodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Shows the farewell message when the user exits.
     */
    public void showGoodbye() {
        System.out.println(goodbyeMessage());
        showLine();
    }

    /**
     * Returns confirmation text after a task is added.
     *
     * @param task task that was added
     * @param taskCount number of tasks now in the list
     * @return confirmation message
     */
    public String taskAddedMessage(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Shows confirmation after a task is added.
     *
     * @param task task that was added
     * @param taskCount number of tasks now in the list
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(taskAddedMessage(task, taskCount));
    }

    /**
     * Returns confirmation text after a task is marked as done.
     *
     * @param task task that was marked
     * @return confirmation message
     */
    public String taskMarkedMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Shows confirmation after a task is marked as done.
     *
     * @param task task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println(taskMarkedMessage(task));
    }

    /**
     * Returns confirmation text after a task is marked as not done.
     *
     * @param task task that was unmarked
     * @return confirmation message
     */
    public String taskUnmarkedMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Shows confirmation after a task is marked as not done.
     *
     * @param task task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(taskUnmarkedMessage(task));
    }

    /**
     * Returns confirmation text after a task is deleted.
     *
     * @param task task that was removed
     * @param taskCount number of tasks remaining in the list
     * @return confirmation message
     */
    public String taskDeletedMessage(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Shows confirmation after a task is deleted.
     *
     * @param task task that was removed
     * @param taskCount number of tasks remaining in the list
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(taskDeletedMessage(task, taskCount));
    }

    /**
     * Returns every task in the list with its one-based position.
     *
     * @param tasks tasks currently stored by Tem
     * @return formatted task list
     */
    public String taskListMessage(TaskList tasks) {
        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append('\n').append(i + 1).append('.').append(tasks.get(i));
        }
        return message.toString();
    }

    /**
     * Shows every task in the list with its one-based position.
     *
     * @param tasks tasks currently stored by Tem
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(taskListMessage(tasks));
    }

    /**
     * Returns tasks whose descriptions match the find keyword.
     *
     * @param tasks tasks currently stored by Tem
     * @param matchingIndices zero-based indices of tasks to display
     * @return formatted matching tasks, or an empty-list message
     */
    public String matchingTasksMessage(TaskList tasks, List<Integer> matchingIndices) {
        if (matchingIndices.isEmpty()) {
            return "No matching tasks in your list.";
        }
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:");
        for (int index : matchingIndices) {
            message.append('\n').append(index + 1).append('.').append(tasks.get(index));
        }
        return message.toString();
    }

    /**
     * Shows tasks whose descriptions match the find keyword.
     *
     * @param tasks tasks currently stored by Tem
     * @param matchingIndices zero-based indices of tasks to display
     */
    public void showMatchingTasks(TaskList tasks, List<Integer> matchingIndices) {
        System.out.println(matchingTasksMessage(tasks, matchingIndices));
    }
}
