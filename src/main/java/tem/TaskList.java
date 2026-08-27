package tem;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the current task list and provides operations to change it.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the given tasks.
     *
     * @param tasks initial tasks to store
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the tasks in this list.
     *
     * @return tasks currently stored
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns how many tasks are in the list.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param index zero-based position in the list
     * @return selected task
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given zero-based index.
     *
     * @param index zero-based position in the list
     * @return removed task
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }
}