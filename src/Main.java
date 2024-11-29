import javafx.stage.Stage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import static javafx.application.Application.launch;

public class Main {

    public static void main(final String[] args) throws Exception {

        boolean stillPlaying = true;
        Scanner sc = new Scanner(System.in);
        World earth = new World();

        while (stillPlaying) {

            System.out.println("Type W to play the Word game");
            System.out.println("Type N to play the Number game");
            System.out.println("Type M to play BattleSweeper");
            System.out.println("Type Q to quit:");

            String input = sc.nextLine();

            switch (input.toUpperCase()) {

                case "W" -> {
                    WordGame wordGame = new WordGame(earth, sc);
                    wordGame.play();
                }

                case "N" -> {
                    new Thread(NumberGame::startNumberGame).start();
                }


                case "M" -> {
                    GameManager gameManager = getGameManager();
                    gameManager.startGame();
                }

                case "Q" -> stillPlaying = false;

                default -> System.out.println("Invalid input, try again.");
            }
        }
        System.out.println("Thank you for playing!");
    }

    private static GameManager getGameManager() {
        int gridSize = 3;

        // Create players
        Player player1 = new HumanPlayer("Player 1", gridSize);
        Player player2 = new HumanPlayer("Player 2", gridSize);

        // Place ships and mines for demonstration
        //player1.placeObject(1, 1, new Ship(2));
        //player2.placeObject(2, 2, new Mine());

        // Start the game
        GameManager gameManager = new GameManager(player1, player2);
        return gameManager;
    }
}
