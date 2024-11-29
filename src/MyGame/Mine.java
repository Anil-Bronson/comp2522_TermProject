class Mine extends GameObject {
    public Mine() {
        this.type = "Mine";
    }

    @Override
    public void interact(Player player) throws GameException {
        throw new GameException(player.getName() + " hit a mine! Turn forfeited.");
    }
}
