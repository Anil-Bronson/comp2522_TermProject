import java.util.*;
import java.util.stream.IntStream;

class Grid<T> {
    private final List<List<Cell<T>>> grid;

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

    public int getSize() {
        return grid.size();
    }

    public Cell<T> getCell(final int x, final int y) {
        return grid.get(x).get(y);
    }

    public void setCell(final int x, final int y, final T value) {
        grid.get(x).get(y).setValue(value);
    }

    public void placeRandomMines(final int numMines) {
        Random random = new Random();
        final Set<String> usedCoordinates = new HashSet<>(); // To ensure unique mine placements

        for (int i = 0; i < numMines; i++) {
            int x, y;
            do {
                x = random.nextInt(getSize()); // Random row
                y = random.nextInt(getSize()); // Random column
            } while (usedCoordinates.contains(x + "," + y)); // Check if already used

            usedCoordinates.add(x + "," + y); // Mark this coordinate as used
            setCell(x, y, (T) new Mine()); // Place a mine
        }
    }
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

                    if (showObjects || ship.isRevealed()) {
                        System.out.print(" S |"); // Show ship if revealed or during setup
                    } else {
                        System.out.print("   |"); // Hide ship
                    }

                } else if (value instanceof Mine) {
                    // Only reveal mines during setup or when a player hits one
                    if (showObjects) {
                        System.out.print(" M |"); // Show mine during setup or on hit
                    } else {
                        System.out.print("   |"); // Hide mine during gameplay
                    }
                } else {
                    System.out.print("   |"); // Empty cell
                }

            }

            System.out.println();
            System.out.print("   ");
            System.out.println("-".repeat(4 * grid.size() + 1));
        }
    }


    public int countNearbyMines(final int x, final int y) {
        final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        return (int) IntStream.range(0, dx.length)
                .mapToObj(i -> new int[]{x + dx[i], y + dy[i]})
                .filter(pos -> pos[0] >= 0 && pos[0] < grid.size() && pos[1] >= 0 && pos[1] < grid.size())
                .map(pos -> getCell(pos[0], pos[1]).getValue())
                .filter(value -> value instanceof Mine)
                .count();  // Counting the mines
    }


}
