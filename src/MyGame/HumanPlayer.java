import java.util.Scanner;

class HumanPlayer extends Player {
    private final Scanner scanner;

    public HumanPlayer(final String name, final int gridSize) {
        super(name, gridSize);
        scanner = new Scanner(System.in);
    }

    @Override
    public void makeMove(final Grid<GameObject> opponentGrid) throws GameException {
        System.out.println(name + ", enter your move (x y): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        GameObject object = opponentGrid.getCell(x, y).getValue();
        if (object != null) {
            object.interact(this);
        } else {
            System.out.println("Miss!");
        }
    }
}
