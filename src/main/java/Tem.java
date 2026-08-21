import java.util.Scanner;

/**
 * A simple command-line assistant that echoes user commands until asked to exit.
 */
public class Tem {
    private static final String DIVIDER = "____________________________________________________________";

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
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(command.equals("bye") ? "Bye. Hope to see you again soon!" : command);
            System.out.println(DIVIDER);

            if (command.equals("bye")) {
                break;
            }
        }
    }
}
