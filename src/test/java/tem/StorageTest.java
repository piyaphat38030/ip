package tem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link Storage}.
 */
public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_missingFile_returnsEmptyList() throws TemException {
        Storage storage = new Storage(tempDir.resolve("missing.txt").toString());
        assertTrue(storage.load().isEmpty());
    }

    @Test
    public void saveAndLoad_roundTrip_preservesTasks() throws TemException {
        Path filePath = tempDir.resolve("tem.txt");
        Storage storage = new Storage(filePath.toString());

        Task todo = new Todo("read book");
        todo.markAsDone();
        Task deadline = new Deadline("return book", LocalDate.of(2019, 10, 15));
        Task event = new Event("meeting", "Mon 2pm", "4pm");

        storage.save(List.of(todo, deadline, event));
        List<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Todo);
        assertTrue(loadedTasks.get(0).isDone());
        assertEquals("read book", loadedTasks.get(0).getDescription());

        assertTrue(loadedTasks.get(1) instanceof Deadline);
        assertEquals(LocalDate.of(2019, 10, 15), ((Deadline) loadedTasks.get(1)).getBy());

        assertTrue(loadedTasks.get(2) instanceof Event);
        assertEquals("meeting", loadedTasks.get(2).getDescription());
    }

    @Test
    public void load_corruptLine_isSkipped() throws Exception {
        Path filePath = tempDir.resolve("tem.txt");
        Files.writeString(filePath, """
                T | 1 | read book
                not a valid line
                D | 0 | return book | 2019-10-15
                """);

        Storage storage = new Storage(filePath.toString());
        List<Task> loadedTasks = storage.load();

        assertEquals(2, loadedTasks.size());
        assertEquals("read book", loadedTasks.get(0).getDescription());
        assertEquals("return book", loadedTasks.get(1).getDescription());
    }
}
