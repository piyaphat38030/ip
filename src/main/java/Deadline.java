/**
 * Represents a task that must be completed by a specified time.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates an incomplete deadline task.
     *
     * @param description description supplied by the user
     * @param by time by which the task should be completed
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns the due time of this deadline.
     *
     * @return due time text
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns this deadline in the on-disk format {@code D | done | description | by}.
     *
     * @return storage line for this deadline
     */
    @Override
    public String toStorageString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
