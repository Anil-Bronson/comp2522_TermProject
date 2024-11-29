/**
 * Represents a mine in the game.
 * This object interacts with the player by forfeiting their turn if they hit it.
 */
class Mine extends GameObject {

    /**
     * Constructs a new Mine object.
     * Sets the type of the mine to "Mine".
     */
    public Mine() {
        this.type = "Mine";
    }

    /**
     * Interacts with the player when they hit a mine.
     * This will throw a GameException, forfeiting the player's turn.
     *
     * @param player The player who hit the mine.
     * @throws GameException If the player hits a mine, an exception is thrown to forfeit their turn.
     */
    @Override
    public void interact(Player player) throws GameException {
        throw new GameException(player.getName() + " hit a mine! Turn forfeited.");
    }
}
