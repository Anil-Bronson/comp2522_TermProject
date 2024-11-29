import java.util.Scanner;

/**
 * Represents a player in the game. Each player has a name, a grid for placing ships and mines,
 * and a method to make moves against an opponent.
 */
class Player {
    protected final  String name;
    protected final Grid<GameObject> grid;
    private int[] lastMove = null;  // Store last move coordinates

    /**
     * Constructs a Player with a name and a grid of the given size.
     *
     * @param name The name of the player.
     * @param gridSize The size of the player's grid.
     */
    public Player(final String name,
                  final int gridSize) {

        this.name = name;
        this.grid = new Grid<>(gridSize);
    }

    /**
     * Getter for the last move coordinates.
     *
     * @return An array containing the x and y coordinates of the last move.
     */
    public int[] getLastMove() {
        return lastMove;  // Return the last move's coordinates
    }

    /**
     * Getter for the player's name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Allows the player to make a move against the opponent's grid.
     * Validates the move, reveals the target cell, and handles interactions with mines and ships.
     *
     * @param opponentGrid The opponent's grid where the player will make a move.
     * @throws GameException If the player hits a mine or makes an invalid move.
     */
    public void makeMove(final Grid<GameObject> opponentGrid) throws GameException {
        final Scanner scanner;
        scanner = new Scanner(System.in);

        System.out.print("Enter x-coordinate for your attack: ");
        int x = scanner.nextInt();

        System.out.print("Enter y-coordinate for your attack: ");
        int y = scanner.nextInt();

        // Check if the move is within bounds
        if (x < 0 || x >= opponentGrid.getSize() || y < 0 || y >= opponentGrid.getSize()) {
            System.out.println("Out of bounds! Try again.");
            return;
        }

        // Save the move coordinates to lastMove
        lastMove = new int[] {x, y};

        // Get the target cell in the opponent's grid
        Cell<GameObject> targetCell = opponentGrid.getCell(x, y);

        // Check if the cell has already been revealed
        if (targetCell.isRevealed()) {
            System.out.println("You've already targeted this cell! Try again.");
            return;
        }

        // Reveal the target cell
        targetCell.reveal();

        // Get the object in the target cell
        GameObject target = targetCell.getValue();

        // If it's a mine, the player loses and the turn is over
        if (target instanceof Mine) {

            System.out.println("Boom! You hit a mine.");
            throw new GameException("You hit a mine and forfeited your turn.");
        }

        // If it's a ship, interact with the ship
        else if (target instanceof Ship ship) {
            ship.interact(this);  // Handle ship interaction (e.g., damaging or sinking)
        }

        // If it's a blank space, the player missed
        else {
            int adjacentMines = opponentGrid.countNearbyMines(x, y);
            System.out.println("Miss! There are " + adjacentMines + " mines nearby.");
        }
    }

    /**
     * Places an object (such as a ship or mine) at the specified coordinates on the player's grid.
     *
     * @param x The x-coordinate of the placement.
     * @param y The y-coordinate of the placement.
     * @param object The object to place (e.g., Ship or Mine).
     * @throws Exception If the coordinates are out of bounds or the cell is already occupied.
     */
    public void placeObject(final int x,
                            final int y,
                            final GameObject object) throws Exception {

        // Ensure the coordinates are within bounds
        if (x < 0 || x >= grid.getSize() || y < 0 || y >= grid.getSize()) {
            throw new Exception("Coordinates out of bounds.");
        }

        // Check if the cell is already occupied
        if (grid.getCell(x, y).getValue() != null) {
            throw new Exception("Cell is already occupied.");
        }

        // Place the object on the grid
        grid.setCell(x, y, object);
    }

    /**
     * Displays the player's grid, optionally showing hidden objects based on the reveal flag.
     *
     * @param showObjects If true, displays hidden objects; otherwise, only reveals what is visible.
     */
    public void displayGrid(boolean showObjects) {

        System.out.println(name + "'s Grid:");
        grid.display(showObjects);  // Display the grid with or without hidden objects
    }

    /**
     * Checks if all ships have been sunk on the opponent's grid.
     *
     * @param grid The opponent's grid to check.
     * @return true if all ships have been sunk, false otherwise.
     */
    public boolean allShipsSunk(final Grid<GameObject> grid) {

        for (int x = 0; x < grid.getSize(); x++) {

            for (int y = 0; y < grid.getSize(); y++) {
                GameObject obj = grid.getCell(x, y).getValue();

                if (obj instanceof Ship ship) {

                    if (!ship.isRevealed()) {
                        return false;  // There is at least one ship remaining
                    }
                }
            }
        }
        return true;  // All ships are sunk
    }

    /**
     * Getter for the player's grid.
     *
     * @return The player's grid.
     */
    public Grid<GameObject> getGrid() {
        return grid;
    }
}
