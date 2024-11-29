class AIPlayer extends Player {
    public AIPlayer(final String name, final int gridSize) {
        super(name, gridSize);
    }

    public void makeMove(final Grid<GameObject> opponentGrid) throws GameException {
        // Generate random moves
        System.out.println(name + " is making a move...");
    }
}