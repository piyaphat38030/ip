import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Interprets user commands and turns them into tasks or task indexes.
 */
public class Parser {

    /**
     * Creates a task from a task-creation command.
     *
     * @param command command entered by the user
     * @return the task described by the command
     * @throws TemException if the command is unknown or required details are missing
     */
    public static Task parseTask(String command) throws TemException {
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
        throw new TemException(
                "I don't recognise that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
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
    public static int parseTaskIndex(String command, TaskList tasks, String action) throws TemException {
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

    private static LocalDate parseDate(String dateText) throws TemException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new TemException("Use a date like 2019-10-15 after /by.");
        }
    }

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

    private static void ensurePresent(String value, String message) throws TemException {
        if (value.isEmpty()) {
            throw new TemException(message);
        }
    }
}
