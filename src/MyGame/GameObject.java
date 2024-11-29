abstract class GameObject {
    protected String type;

    public abstract void interact(final Player player) throws GameException;

    @Override
    public String toString() {
        return type;
    }
}
