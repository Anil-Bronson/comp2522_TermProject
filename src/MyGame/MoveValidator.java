/**
 * A functional interface for validating player moves on the grid.
 * This interface defines a single method to check if a move is valid based on the coordinates.
 */
@FunctionalInterface
interface MoveValidator {

    /**
     * Validates the move based on the given coordinates.
     * This method should be implemented to define the criteria for a valid move.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     * @return true if the move is valid, false otherwise.
     */
    boolean isValidMove(int x, int y);
}
