/**
 * Abstract class representing a game object. This class serves as a base for various objects
 * that can be placed on the game grid, such as ships and mines. It defines the basic structure
 * for all game objects, including the type of the object and how it interacts with players.
 */
abstract class GameObject {
    protected String type;  // The type of the game object (e.g., "Ship", "Mine")

    /**
     * Defines the interaction between the game object and a player.
     * This method must be implemented by subclasses to specify
     * how different game objects interact with players.
     *
     * @param player The player interacting with the game object.
     * @throws GameException If the interaction results in an error or invalid action.
     */
    public abstract void interact(final Player player) throws GameException;

    /**
     * Returns a string representation of the game object.
     * This representation is based on the type of the game object.
     *
     * @return The type of the game object as a string.
     */
    @Override
    public String toString() {
        return type;
    }
}
