package tem;

/**
 * Represents the fixed task categories Tem knows how to store.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String displaySymbol;

    TaskType(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    /**
     * Returns the letter used when displaying a task of this type.
     *
     * @return task type symbol shown to the user
     */
    public String getDisplaySymbol() {
        return displaySymbol;
    }
}