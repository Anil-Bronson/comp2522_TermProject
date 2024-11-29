import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents a game grid that holds cells containing game objects.
 * The grid is used for placing and displaying ships, mines, and other game objects.
 * This class provides methods for managing and interacting with the grid,
 * including placing mines, displaying the grid, and counting nearby mines.
 *
 * @param <T> The type of the objects placed in the grid (e.g., Ship, Mine).
 */
class Grid<T> {

    private final List<List<Cell<T>>> grid;

    /**
     * Constructs a grid of the given size, initializing each cell with a null value.
     *
     * @param size The size of the grid (number of rows and columns).
     */
    public Grid(final int size) {
        grid = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Cell<T>> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(new Cell<>());
            }
            grid.add(row);
        }
    }

    /**
     * Returns the size of the grid (number of rows or columns).
     *
     * @return The size of the grid.
     */
    public int getSize() {
        return grid.size();
    }

    /**
     * Returns the cell at the specified coordinates in the grid.
     *
     * @param x The row index of the cell.
     * @param y The column index of the cell.
     * @return The cell at the specified coordinates.
     */
    public Cell<T> getCell(final int x,
                           final int y) {

        return grid.get(x).get(y);
    }

    /**
     * Sets the value of the cell at the specified coordinates.
     *
     * @param x The row index of the cell.
     * @param y The column index of the cell.
     * @param value The value to set in the cell.
     */
    public void setCell(final int x,
                        final int y,
                        final T value) {

        grid.get(x).get(y).setValue(value);
    }

    /**
     * Places a random number of mines on the grid.
     * The mines are placed at unique coordinates to avoid overlap.
     *
     * @param numMines The number of mines to place.
     */
    public void placeRandomMines(final int numMines) {

        Random random = new Random();
        final Set<String> usedCoordinates = new HashSet<>(); // To ensure unique mine placements

        IntStream.range(0, numMines).forEach(i -> {
            int x, y;
            do {
                x = random.nextInt(getSize()); // Random row
                y = random.nextInt(getSize()); // Random column
            } while (usedCoordinates.contains(x + "," + y)); // Check if already used

            usedCoordinates.add(x + "," + y); // Mark this coordinate as used
            setCell(x, y, (T) new Mine()); // Place a mine
        });
    }

    /**
     * Displays the grid in a user-friendly format.
     * Shows or hides objects based on the provided `showObjects` flag.
     *
     * @param showObjects If true, shows objects like ships and mines; otherwise, hides them.
     */
    public void display(final boolean showObjects) {
        System.out.print("     ");

        for (int j = 0; j < grid.size(); j++) {
            System.out.print(j + "   ");
        }
        System.out.println();

        System.out.print("   ");
        System.out.println("-".repeat(4 * grid.size() + 1));

        for (int i = 0; i < grid.size(); i++) {
            System.out.print(" " + i + " |");

            for (int j = 0; j < grid.get(i).size(); j++) {

                T value = grid.get(i).get(j).getValue();

                if (value instanceof Ship ship) {
                    // Show ship if revealed or during setup
                    System.out.print((showObjects || ship.isRevealed()) ? " S |" : "   |");
                } else if (value instanceof Mine) {
                    // Show mine during setup or on hit
                    System.out.print(showObjects ? " M |" : "   |");
                } else {
                    // Empty cell
                    System.out.print("   |");
                }
            }

            System.out.println();
            System.out.print("   ");
            System.out.println("-".repeat(4 * grid.size() + 1));
        }
    }

    /**
     * Counts the number of mines in the neighboring cells of the specified coordinates.
     *
     * @param x The row index of the target cell.
     * @param y The column index of the target cell.
     * @return The number of mines in neighboring cells.
     */
    public int countNearbyMines(final int x,
                                final int y) {

        final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        return (int) IntStream.range(0, dx.length)
                .mapToObj(i -> new int[]{x + dx[i], y + dy[i]})
                .filter(pos -> pos[0] >= 0 && pos[0] < grid.size() && pos[1] >= 0 && pos[1] < grid.size())
                .map(pos -> getCell(pos[0], pos[1]).getValue())
                .filter(value -> value instanceof Mine)
                .count();  // Counting the mines
    }

    /**
     * Resets the grid by clearing all cells, removing any objects (mines, ships).
     */
    public void reset() {
        grid.forEach(row -> row.forEach(cell -> cell.setValue(null))); // Clear all cells
    }
}
