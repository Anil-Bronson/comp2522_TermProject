/**
 * Represents a single cell in the game grid that can store a value and track whether it's revealed or not.
 *
 * @param <T> The type of value the cell can store (e.g., {@link Ship}, {@link Mine}, or null for empty).
 */
final class Cell<T> {
    private T value;  // The value stored in the cell (e.g., a Ship, Mine, or null)
    private boolean revealed;  // Whether the cell has been revealed during gameplay
    private static final int FIRST_CHAR = 0;  // Index for extracting the first character of the value's string representation

    /**
     * Constructs a new {@code Cell} with no value and as not revealed.
     */
    public Cell() {
        this.value = null;
        this.revealed = false;
    }

    /**
     * Returns the value stored in the cell.
     *
     * @return The value of the cell (e.g., Ship, Mine, or null).
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value of the cell.
     *
     * @param value The value to store in the cell (e.g., a Ship, Mine, or null).
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns whether the cell has been revealed.
     *
     * @return {@code true} if the cell is revealed; {@code false} otherwise.
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Reveals the cell, marking it as visible to the player.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Returns a string representation of the cell.
     * If the cell is revealed:
     * - If it contains a value, it returns the first character of the value's string representation.
     * - If it is empty, it returns 'O' to represent a miss.
     * If the cell is not revealed, it returns '.'.
     *
     * @return A string representing the cell, either '.' (hidden), 'O' (miss), or the first character of the value.
     */
    @Override
    public String toString() {
        if (revealed) {
            if (value == null) {
                return "O"; // 'O' for miss if the cell has no value
            }
            else {
                return value.toString().charAt(FIRST_CHAR) + ""; // First character of the GameObject type
            }
        }
        return " "; // Return ' ' if the cell is not revealed
    }
}
