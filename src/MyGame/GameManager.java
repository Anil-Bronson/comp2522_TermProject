import java.util.Scanner;

class GameManager {

    private final Player player1;
    private final Player player2;

    private static GameManager instance;
    private static final int MAX_MINES = 3;
    GameManager(final Player player1,
               final Player player2) {

       this.player1 = player1;
       this.player2 = player2;
    }

    public static synchronized GameManager getInstance(final Player player1,
                                                       final Player player2) {
        if (instance == null) {
            instance = new GameManager(player1, player2);
        }
        return instance;
        }


    public void startGame() {
        final Scanner scanner;
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Game!");

            // Place 3 random mines on each grid
            System.out.println("Placing mines on the grids...");

            player1.getGrid().placeRandomMines(MAX_MINES);
            player2.getGrid().placeRandomMines(MAX_MINES);

            // Setup phase: Players place ships
            setupPhase(player1);
            setupPhase(player2);

            System.out.println("Game started!");

            int player1MineHits   = 0;
            int player2MineHits   = 0;
            final int maxMineHits = MAX_MINES; // Game over if a player hits this many mines

            while (true) {
                try {
                    System.out.println("Player 1's grid:");
                    player1.getGrid().display(false); // Don't show hidden objects
                    System.out.println("Player 1's turn:");
                    player1.makeMove(player2.getGrid());

                    // Check if Player 1 hit a mine
                    if (player1.getLastMove() != null) {

                        int[] move = player1.getLastMove();

                        if (player2.getGrid().getCell(move[0], move[1]).getValue() instanceof Mine) {

                            System.out.println("Player 1 hit a mine! Mines revealed.");

                            // Show both players' grids with mines revealed
                            player1.getGrid().display(true);
                            player2.getGrid().display(true);
                        }
                    }

                    if (player1.allShipsSunk(player2.getGrid())) {
                        System.out.println("Player 1 wins! All of Player 2's ships have been sunk.");
                        break;
                    }

                } catch (GameException e) {
                    player1MineHits++;
                    System.out.println("Player 1 hit a mine! Total mines hit: " + player1MineHits);

                    if (player1MineHits >= maxMineHits) {
                        System.out.println("Player 1 loses! You hit too many mines.");
                        break;
                    }
                }

                try {
                    System.out.println("Player 2's grid:");
                    player2.getGrid().display(false); // Don't show hidden objects

                    System.out.println("Player 2's turn:");
                    player2.makeMove(player1.getGrid());

                    // Check if Player 2 hit a mine
                    if (player2.getLastMove() != null) {
                        int[] move = player2.getLastMove();

                        if (player1.getGrid().getCell(move[0], move[1]).getValue() instanceof Mine) {
                            System.out.println("Player 2 hit a mine! Mines revealed.");

                            // Show both players' grids with mines revealed
                            player1.getGrid().display(true);
                            player2.getGrid().display(true);
                        }
                    }

                    if (player2.allShipsSunk(player1.getGrid())) {
                        System.out.println("Player 2 wins! All of Player 1's ships have been sunk.");
                        break;
                    }
                } catch (GameException e) {
                    player2MineHits++;
                    System.out.println("Player 2 hit a mine! Total mines hit: " + player2MineHits);

                    if (player2MineHits >= maxMineHits) {
                        System.out.println("Player 2 loses! You hit too many mines.");
                        break;
                    }
                }
            }

            // After the game ends, prompt if they want to play again
            System.out.print("Do you want to play again? (y/n): ");
            String replayChoice = scanner.next();

            if (!replayChoice.equalsIgnoreCase("y")) {

                System.out.println("Thanks for playing!");
                break; // Exit the game loop if the user doesn't want to play again
            } else {

                // Reset the players and grids for a new game
                player1.getGrid().reset();
                player2.getGrid().reset();
            }
        }
    }

    private void setupPhase(final Player player) {
        System.out.println(player.getName() + ", place your ships!");
        player.displayGrid(true); // Show grid with ships during setup

        for (int i = 1; i <= 3; i++) {
            System.out.println("Placing ship " + i + ":");
            while (true) {
                try {

                    int x = getInput("Enter x-coordinate: ");
                    int y = getInput("Enter y-coordinate: ");

                    player.placeObject(x, y, new Ship());
                    break;

                } catch (final Exception e) {
                    System.out.println("Invalid position. Try again.");
                }
            }
            player.displayGrid(true); // Update grid after each placement
        }
    }

    private int getInput(final String prompt) {
        System.out.print(prompt);
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
