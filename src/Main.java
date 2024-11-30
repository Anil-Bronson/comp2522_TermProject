import java.util.Scanner;

public class Main {

    public static void main(final String[] args) throws Exception {

        final Scanner sc;

        boolean stillPlaying = true;
        sc = new Scanner(System.in);


        while (stillPlaying) {

            System.out.println("Type W to play the Word game");
            System.out.println("Type N to play the Number game");
            System.out.println("Type M to play BattleSweeper");
            System.out.println("Type Q to quit:");

            String input = sc.nextLine();

            switch (input.toUpperCase()) {

                case "W" -> {
                    final World earth;
                    earth = new World();
                    WordGame wordGame = new WordGame(earth, sc);
                    wordGame.play();
                }

                case "N" -> {
                    new Thread(NumberGame::startNumberGame).start();
                }


                case "M" -> {
                    final GameManager gameManager;
                    gameManager = getGameManager();
                    gameManager.startGame();
                }

                case "Q" -> stillPlaying = false;

                default -> System.out.println("Invalid input, try again.");
            }
        }
        System.out.println("Thank you for playing!");
    }

    private static GameManager getGameManager() {

        final int gridSize = 5;
        final     Player player1;
        final     Player player2;
        final     GameManager gameManager;

        // Create players & game instance
        player1     = new HumanPlayer("Player 1", gridSize);
        player2     = new HumanPlayer("Player 2", gridSize);
        gameManager = new GameManager(player1, player2);

        return gameManager;
    }
}
