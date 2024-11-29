import java.util.Scanner;

/**
 * Represents a human player in the game.
 * This player can interact with the game grid by making a move where the player enters coordinates.
 */
class HumanPlayer extends Player {
    private final Scanner scanner;

    /**
     * Constructs a new HumanPlayer with the specified name and grid size.
     * Initializes the scanner to read user input.
     *
     * @param name The name of the player.
     * @param gridSize The size of the grid.
     */
    public HumanPlayer(final String name,
                       final int gridSize) {

        super(name, gridSize);
        scanner = new Scanner(System.in);
    }

    /**
     * Makes a move on the opponent's grid by prompting the user to enter the coordinates.
     * If the coordinates correspond to an object, the player interacts with it.
     * Otherwise, the player misses.
     *
     * @param opponentGrid The opponent's grid on which the move is made.
     * @throws GameException If there is an error during the interaction (e.g., invalid move).
     */
    @Override
    public void makeMove(final Grid<GameObject> opponentGrid) throws GameException {

        System.out.println(name + ", enter your move (x y): ");

        int x = scanner.nextInt();  // Read the x-coordinate
        int y = scanner.nextInt();  // Read the y-coordinate

        // Retrieve the object at the specified coordinates on the opponent's grid
        GameObject object = opponentGrid.getCell(x, y).getValue();

        // If an object is found at the location, interact with it
        if (object != null) {
            object.interact(this);
        }
        else {
            // If no object is found at the location, print a miss message
            System.out.println("Miss!");
        }
    }
}
