/**
 * Represents a task with no associated date or time.
 */
public class Todo extends Task {

    /**
     * Creates an incomplete to-do task.
     *
     * @param description description supplied by the user
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTaskTypeIcon() {
        return "T";
    }
}
