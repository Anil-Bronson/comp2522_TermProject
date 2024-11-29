/**
 * Represents a player controlled by artificial intelligence (AI).
 * This class extends {@link Player} and overrides the {@link Player#makeMove(Grid)} method
 * to allow the AI to make moves on the game grid.
 */
class AIPlayer extends Player {

    /**
     * Constructs an {@code AIPlayer} object with a given name and grid size.
     * This constructor calls the parent class {@link Player}'s constructor to initialize
     * the name and grid size for the AI player.
     *
     * @param name     The name of the AI player.
     * @param gridSize The size of the game grid.
     */
    public AIPlayer(final String name, final int gridSize) {
        super(name, gridSize);
    }

    /**
     * Makes a move on the opponent's grid by the AI player.
     * The AI generates a random move. This is a simplified version, and
     * more complex AI logic could be implemented here for smarter decision-making.
     *
     * @param opponentGrid The opponent's game grid where the move will be made.
     * @throws GameException If an error occurs while making the move.
     */
    public void makeMove(final Grid<GameObject> opponentGrid) throws GameException {
        // Generate random moves for the AI player (simplified logic)
        System.out.println(name + " is making a move...");
    }
}
