package tem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads and saves Tem's task list using a text file under the project folder.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a storage helper for the given relative file path.
     *
     * @param filePath relative path from the project root, for example {@code data/tem.txt}
     */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Reads tasks from disk. Missing folders or files are treated as an empty task list.
     * Lines that do not match the expected format are skipped.
     *
     * @return tasks loaded from the save file
     * @throws TemException if the file exists but cannot be read
     */
    public List<Task> load() throws TemException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (IOException exception) {
            throw new TemException("Could not read saved tasks from " + filePath + ".");
        }
    }

    /**
     * Writes the full task list to disk, creating parent folders when needed.
     *
     * @param tasks tasks to save
     * @throws TemException if the file or its parent folder cannot be written
     */
    public void save(List<Task> tasks) throws TemException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toStorageString());
            }
            Files.write(filePath, lines);
        } catch (IOException exception) {
            throw new TemException("Could not save tasks to " + filePath + ".");
        }
    }

    /**
     * Converts one storage line into a task, or {@code null} when the line is blank or corrupt.
     *
     * @param line raw line from the save file
     * @return parsed task, or {@code null} if the line should be ignored
     */
    private Task parseLine(String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        if (description.isEmpty()) {
            return null;
        }

        Task task;
        switch (type) {
        case "T":
            if (parts.length != 3) {
                return null;
            }
            task = new Todo(description);
            break;
        case "D":
            if (parts.length != 4 || parts[3].isEmpty()) {
                return null;
            }
            try {
                task = new Deadline(description, LocalDate.parse(parts[3]));
            } catch (DateTimeParseException exception) {
                return null;
            }
            break;
        case "E":
            if (parts.length != 5 || parts[3].isEmpty() || parts[4].isEmpty()) {
                return null;
            }
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}