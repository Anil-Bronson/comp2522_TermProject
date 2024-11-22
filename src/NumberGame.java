import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class NumberGame extends Application implements DisplayOutcomes {
    private int correctPlacements;
    private int incorrectGuesses;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private float averageCorrectPlacements;

    @Override
    public void start(Stage stage) {
        Random rand = new Random();
        int computerGuess = rand.nextInt(1000); // Random computer guess
        int[] guesses = new int[20]; // Array to hold user guesses
        Label label = new Label("Next Number: " + computerGuess);

        // Initialize guesses array to -1 to represent unguessed values
        for (int i = 0; i < guesses.length; i++) {
            guesses[i] = -1;
        }

        // GridPane for buttons
        GridPane grid = new GridPane();

        // Setup the buttons and link them to the guesses array
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                int index = row * 5 + col; // Map button to guesses array index
                Button button = new Button("[ ]");
                button.setPrefSize(100, 50);

                // Add action to the button
                button.setOnAction(event -> {
                    // Simulate a random guess for demo purposes
                    guesses[index] = rand.nextInt(100); // Update guesses array
                    button.setText(String.valueOf(guesses[index])); // Update button text
                });

                // Add button to the grid
                grid.add(button, col, row);
            }
        }

        grid.setHgap(5); // Spacing between columns
        grid.setVgap(5); // Spacing between rows

        // Use a BorderPane to arrange Label and GridPane
        BorderPane root = new BorderPane();
        root.setTop(label); // Add Label to the top
        root.setCenter(grid); // Add GridPane to the center

        // Scene and stage setup
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Number Game");
        stage.show();
    }

    @Override
    public int correctPlacements() {
        return correctPlacements;
    }

    @Override
    public int numGamesPlayed() {
        return gamesPlayed;
    }

    @Override
    public int numGamesWon() {
        return gamesWon;
    }

    @Override
    public int numGamesLost() {
        return gamesLost;
    }
}
