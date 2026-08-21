/**
 * Represents one task in Tem's task list and whether it has been completed.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description description supplied by the user
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void unmarkAsDone() {
        isDone = false;
    }

    /**
     * Returns the symbol used when displaying this task's completion status.
     *
     * @return {@code X} for a completed task, or a space for an incomplete task
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the letter identifying this task's type.
     *
     * @return task type letter
     */
    protected abstract String getTaskTypeIcon();

    /**
     * Returns this task in the format shown to the user.
     *
     * @return task type, completion status, and description
     */
    @Override
    public String toString() {
        return "[" + getTaskTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }
}
