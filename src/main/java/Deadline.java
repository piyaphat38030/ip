import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specified date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    private final LocalDate by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description description supplied by the user
     * @param by date by which the task should be completed
     */
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns the due date of this deadline.
     *
     * @return due date
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns this deadline in the on-disk format {@code D | done | description | yyyy-MM-dd}.
     *
     * @return storage line for this deadline
     */
    @Override
    public String toStorageString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }
}
