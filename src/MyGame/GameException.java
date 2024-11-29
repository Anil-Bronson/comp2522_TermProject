/**
 * Custom exception class for handling game-specific errors.
 * This class extends {@link Exception} to provide a custom exception
 * that can be used in the game for error handling related to game rules or actions.
 */
class GameException extends Exception {

    /**
     * Constructor for {@code GameException}.
     *
     * @param message A description of the exception. This message provides
     *                details about the error that occurred in the game.
     */
    public GameException(final String message) {
        super(message);  // Pass the message to the parent Exception class
    }
}
