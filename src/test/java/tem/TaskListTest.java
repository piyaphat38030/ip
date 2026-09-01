package tem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TaskList}.
 */
public class TaskListTest {

    @Test
    public void constructor_varargs_storesTasksInOrder() {
        TaskList tasks = new TaskList(new Todo("first"), new Todo("second"));
        assertEquals(2, tasks.size());
        assertEquals("first", tasks.get(0).getDescription());
        assertEquals("second", tasks.get(1).getDescription());
    }

    @Test
    public void addAll_varargs_appendsTasks() {
        TaskList tasks = new TaskList();
        tasks.addAll(new Todo("read"), new Todo("write"));
        assertEquals(2, tasks.size());
        assertEquals("read", tasks.get(0).getDescription());
        assertEquals("write", tasks.get(1).getDescription());
    }
}
