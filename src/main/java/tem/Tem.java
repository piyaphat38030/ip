package tem;

/**
 * A simple command-line assistant that stores tasks until asked to exit.
 */
public class Tem {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates Tem using the given relative save-file path.
     *
     * @param filePath relative path to the task save file, for example {@code data/tem.txt}
     */
    public Tem(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (TemException exception) {
            ui.showLoadingError(exception.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts Tem and processes commands until the user exits.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            String command = ui.readCommand();
            if (command == null) {
                break;
            }

            if (command.equals("bye")) {
                ui.showGoodbye();
                break;
            }

            try {
                handleCommand(command);
            } catch (TemException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
    }

    /**
     * Starts Tem with the default save file location.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        new Tem("data/tem.txt").run();
    }

    private void handleCommand(String command) throws TemException {
        if (command.equals("list")) {
            ui.showTaskList(tasks);
            return;
        }
        if (command.equals("mark") || command.startsWith("mark ")) {
            Task task = tasks.get(Parser.parseTaskIndex(command, tasks, "mark as done"));
            task.markAsDone();
            storage.save(tasks.getTasks());
            ui.showTaskMarked(task);
            return;
        }
        if (command.equals("unmark") || command.startsWith("unmark ")) {
            Task task = tasks.get(Parser.parseTaskIndex(command, tasks, "mark as not done"));
            task.unmarkAsDone();
            storage.save(tasks.getTasks());
            ui.showTaskUnmarked(task);
            return;
        }
        if (command.equals("delete") || command.startsWith("delete ")) {
            Task deletedTask = tasks.delete(Parser.parseTaskIndex(command, tasks, "delete"));
            storage.save(tasks.getTasks());
            ui.showTaskDeleted(deletedTask, tasks.size());
            return;
        }
        if (command.equals("find") || command.startsWith("find ")) {
            String keyword = Parser.parseFindKeyword(command);
            ui.showMatchingTasks(tasks, tasks.findMatchingIndices(keyword));
            return;
        }

        Task task = Parser.parseTask(command);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}