package tem;

/**
 * A simple assistant that stores tasks until asked to exit.
 */
public class Tem {
    private static final String DATA_FILE_PATH = "data/tem.txt";

    private final Storage storage;
    private final Ui ui;
    private final TaskList tasks;
    private final String loadingErrorMessage;

    /**
     * Creates Tem using the default save-file location.
     */
    public Tem() {
        this(DATA_FILE_PATH);
    }

    /**
     * Creates Tem using the given relative save-file path.
     *
     * @param filePath relative path to the task save file, for example {@code data/tem.txt}
     */
    public Tem(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        String loadError = null;
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (TemException exception) {
            loadedTasks = new TaskList();
            loadError = exception.getMessage();
        }
        tasks = loadedTasks;
        loadingErrorMessage = loadError;
    }

    /**
     * Starts Tem and processes commands until the user exits.
     */
    public void run() {
        ui.showWelcome();
        if (loadingErrorMessage != null) {
            ui.showLoadingError(loadingErrorMessage);
        }
        while (true) {
            String command = ui.readCommand();
            if (command == null) {
                break;
            }
            String response = getResponse(command);
            System.out.println(response);
            if (isExit(command)) {
                ui.showLine();
                break;
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
        new Tem().run();
    }

    /**
     * Returns the welcome message shown once at startup.
     *
     * @return welcome text
     */
    public String welcomeMessage() {
        if (loadingErrorMessage != null) {
            return ui.welcomeMessage() + "\n\n" + ui.loadingErrorMessage(loadingErrorMessage);
        }
        return ui.welcomeMessage();
    }

    /**
     * Returns true if the given input is the bye command.
     *
     * @param input raw user input
     * @return whether the input requests an exit
     */
    public boolean isExit(String input) {
        return input.trim().equals("bye");
    }

    /**
     * Processes one line of user input and returns Tem's response text.
     *
     * @param input raw user input
     * @return response to show the user
     */
    public String getResponse(String input) {
        String command = input.trim();
        if (command.isEmpty()) {
            return "Please enter a command.";
        }
        if (command.equals("bye")) {
            return ui.goodbyeMessage();
        }

        try {
            return executeCommand(command);
        } catch (TemException exception) {
            return exception.getMessage();
        }
    }

    private String executeCommand(String command) throws TemException {
        if (command.equals("list")) {
            return ui.taskListMessage(tasks);
        }
        if (command.equals("mark") || command.startsWith("mark ")) {
            return withSave(markTask(command));
        }
        if (command.equals("unmark") || command.startsWith("unmark ")) {
            return withSave(unmarkTask(command));
        }
        if (command.equals("delete") || command.startsWith("delete ")) {
            return withSave(deleteTask(command));
        }
        if (command.equals("find") || command.startsWith("find ")) {
            String keyword = Parser.parseFindKeyword(command);
            return ui.matchingTasksMessage(tasks, tasks.findMatchingIndices(keyword));
        }

        Task task = Parser.parseTask(command);
        tasks.add(task);
        storage.save(tasks.getTasks());
        return ui.taskAddedMessage(task, tasks.size());
    }

    private String withSave(String response) throws TemException {
        storage.save(tasks.getTasks());
        return response;
    }

    private String markTask(String command) throws TemException {
        Task task = tasks.get(Parser.parseTaskIndex(command, tasks, "mark as done"));
        task.markAsDone();
        return ui.taskMarkedMessage(task);
    }

    private String unmarkTask(String command) throws TemException {
        Task task = tasks.get(Parser.parseTaskIndex(command, tasks, "mark as not done"));
        task.unmarkAsDone();
        return ui.taskUnmarkedMessage(task);
    }

    private String deleteTask(String command) throws TemException {
        Task deletedTask = tasks.delete(Parser.parseTaskIndex(command, tasks, "delete"));
        return ui.taskDeletedMessage(deletedTask, tasks.size());
    }
}
