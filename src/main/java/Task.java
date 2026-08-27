/**
 * Represents one task in Tem's task list and whether it has been completed.
 */
public abstract class Task {
    private final String description;
    private final TaskType taskType;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description description supplied by the user
     * @param taskType fixed type of this task
     */
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
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
     * Returns whether this task has been marked as done.
     *
     * @return {@code true} if the task is completed
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the task description entered by the user.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the fixed type of this task.
     *
     * @return task type
     */
    public TaskType getTaskType() {
        return taskType;
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
     * Returns one line used when saving this task to disk.
     *
     * @return storage line for this task
     */
    public abstract String toStorageString();

    /**
     * Returns this task in the format shown to the user.
     *
     * @return task type, completion status, and description
     */
    @Override
    public String toString() {
        return "[" + taskType.getDisplaySymbol() + "][" + getStatusIcon() + "] " + description;
    }
}
