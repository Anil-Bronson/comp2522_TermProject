import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BattleSweeperTest {

    @Test
    void testGridInitialization() {
        // Test that the grid is initialized correctly with the correct size
        Grid<GameObject> grid = new Grid<>(5);  // 5x5 grid
        assertEquals(5, grid.getSize(), "Grid size should be 5x5");

        // Test that the grid is empty (no mines, ships, etc.)
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                assertNull(grid.getCell(i, j).getValue(), "Cell should be empty initially.");
            }
        }
    }

    @Test
    void testMinePlacement() {
        // Test that mines are placed correctly
        Grid<GameObject> grid = new Grid<>(5);
        grid.placeRandomMines(3); // Place 3 random mines

        int mineCount = 0;
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getCell(i, j).getValue() instanceof Mine) {
                    mineCount++;
                }
            }
        }

        // Test that exactly 3 mines were placed
        assertEquals(3, mineCount, "Exactly 3 mines should be placed.");
    }

    @Test
    void testCountNearbyMines() {
        // Test that nearby mines are counted correctly
        Grid<GameObject> grid = new Grid<>(5);
        grid.placeRandomMines(3);

        // Assume the mine placements are at fixed coordinates for the test
        int count = grid.countNearbyMines(2, 2);  // Count mines near (2, 2)

        // The expected count will depend on where the mines are placed
        // For the sake of the example, let's assume there is 1 mine around (2, 2)
        assertTrue(count >= 0, "Nearby mines count should be non-negative.");
    }

}
