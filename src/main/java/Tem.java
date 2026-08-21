import java.util.Scanner;

/**
 * A simple command-line assistant that stores tasks until asked to exit.
 */
public class Tem {
    private static final String DIVIDER = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    /**
     * Starts Tem and processes commands entered through standard input.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = " _____                 \n"
                + "|_   _|__ _ __ ___     \n"
                + "  | |/ _ \\ '_ ` _ \\    \n"
                + "  | |  __/ | | | | |   \n"
                + "  |_|\\___|_| |_| |_|   \n";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm Tem.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);

        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER);
                break;
            }

            if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
            }
            System.out.println(DIVIDER);
        }
    }
}
