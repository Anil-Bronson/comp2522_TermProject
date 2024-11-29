@FunctionalInterface
interface MoveValidator {
    boolean isValidMove(int x, int y);
}

// Example usage
//moveValidator validator = (x, y) -> x >= 0 && y >= 0;
