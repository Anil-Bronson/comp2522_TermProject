import java.util.Scanner;

class Player {
    protected String name;
    protected Grid<GameObject> grid;
    private int[] lastMove = null;  // Store last move coordinates

    public Player(String name, int gridSize) {
        this.name = name;
        this.grid = new Grid<>(gridSize);
    }

    // Getter for last move coordinates
    public int[] getLastMove() {
        return lastMove;  // Return the last move's coordinates
    }

    public String getName() {
        return name;
    }

    public void makeMove(Grid<GameObject> opponentGrid) throws GameException {
        Scanner scanner = new Scanner(System.in);
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

    // Method to place an object (e.g., ship or mine) on the player's grid
    public void placeObject(int x, int y, GameObject object) throws Exception {
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

    // Method to display the player's grid, optionally showing hidden objects
    public void displayGrid(boolean showObjects) {
        System.out.println(name + "'s Grid:");
        grid.display(showObjects);  // Display the grid with or without hidden objects
    }

    // Check if all ships have been sunk on the opponent's grid
    public boolean allShipsSunk(Grid<GameObject> grid) {
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

    public Grid<GameObject> getGrid() {
        return grid;
    }
}
