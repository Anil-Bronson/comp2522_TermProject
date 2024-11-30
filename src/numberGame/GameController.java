import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;

/**
 * The GameController class is responsible for managing the logic of the game, including initializing
 * the game board, handling user interactions, checking for victory conditions, and updating the game state.
 * It manages the placement of values on a grid of buttons, updates valid button positions, and tracks
 * statistics such as games played, games won, and successful placements.
 * <p>
 * This class also handles displaying alerts at the end of the game, showing the final score, and
 * resetting the game board when necessary. It integrates with the JavaFX framework to provide a
 * user-friendly interface and respond to button press events.
 * </p>
 */

public class GameController {

    @FXML
    private Label gameMessage;
    @FXML
    private GridPane buttonGrid;
    @FXML
    private GameGridButton[] gridButtons;

    private ArrayList<Integer> validPositions;
    private int currentRandomValue;
    private int gamesPlayed                 = 0;
    private int gamesWon                    = 0;
    private int successfulPlacements        = 0;

    private static final int TOTAL_BUTTONS  = 20;
    private static final int GRID_ROWS      = 5;
    private static final int GRID_COLS      = 5;

    /**
     * Initializes the game board by setting up buttons, assigning event handlers,
     * and preparing the game state for the first move.
     */
    @FXML
    public void initialize() {

        gridButtons = new GameGridButton[TOTAL_BUTTONS];
        validPositions = new ArrayList<>();

        for (int i = 0; i < TOTAL_BUTTONS; i++) {

            gridButtons[i] = new InactiveGridButton();
            gridButtons[i].setMaxWidth(Double.MAX_VALUE);
            gridButtons[i].setMaxHeight(Double.MAX_VALUE);

            int position = i;
            gridButtons[i].setOnAction(event -> onButtonPressed(position, event));

            int row = i / GRID_ROWS;
            int col = i % GRID_COLS;
            buttonGrid.add(gridButtons[i], col, row);
        }

        prepareNextMove();
    }

    /**
     * Handles the action when a button on the grid is pressed, validating the button's position
     * and activating it with the current random value if valid.
     *
     * @param position the position of the button that was pressed
     * @param event the ActionEvent triggered by the button press
     */
    private void onButtonPressed(final int position,
                                 final ActionEvent event) {

        if (gridButtons[position].isButtonValid(position, validPositions)) {

            ActivatedGridButton activatedButton = new ActivatedGridButton(currentRandomValue);
            gridButtons[position] = activatedButton;
            replaceButton(position, activatedButton);
            prepareNextMove();

        }
    }

    /**
     * Replaces the button at the specified position with an activated button, updating the grid.
     *
     * @param position the position of the button to replace
     * @param activatedButton the new activated button to display
     */
    private void replaceButton(final int position,
                               final ActivatedGridButton activatedButton) {
        int row = position / GRID_ROWS;
        int col = position % GRID_COLS;

        buttonGrid.getChildren().removeIf(node ->
                GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col
        );

        buttonGrid.add(activatedButton, col, row);
    }

    /**
     * Prepares for the next move in the game, checking for victory, generating a random value,
     * and updating valid positions.
     */
    private void prepareNextMove() {
        if (checkForVictory()) {
            gamesWon++;
            gamesPlayed++;
            successfulPlacements += countPlacements();
            showEndGameAlert("Victory!", "Congratulations! You've won the game!");
        }

        else {
            generateRandomValue();
            updateValidPositions();
        }
    }

    /**
     * Checks if all buttons have been activated, indicating that the player has won the game.
     *
     * @return true if all buttons are activated (victory), false otherwise
     */
    private boolean checkForVictory() {

        for (GameGridButton button : gridButtons) {

            if (!(button instanceof ActivatedGridButton)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Generates a random value to be placed on the grid.
     */
    private void generateRandomValue() {

        Random random = new Random();
        currentRandomValue = random.nextInt(1000) + 1;

    }

    /**
     * Updates the list of valid positions where the current random value can be placed on the grid.
     */
    private void updateValidPositions() {

        validPositions.clear();

        for (int i = 0; i < TOTAL_BUTTONS; i++) {

            if (isPositionValidBefore(i) && isPositionValidAfter(i)) {

                if (!(gridButtons[i] instanceof ActivatedGridButton)) {
                    validPositions.add(i);
                }
            }
        }

        if (validPositions.isEmpty()) {
            gamesPlayed++;
            successfulPlacements += countPlacements();
            showEndGameAlert("Game Over", "No valid positions available for " + currentRandomValue);
        }

        else {
            gameMessage.setText("Place the value: " + currentRandomValue);
        }
    }

    /**
     * Checks if the position is valid before the current position, ensuring that the values placed
     * on the grid are in ascending order before the given position.
     *
     * @param position the position to check
     * @return true if the position is valid, false otherwise
     */
    private boolean isPositionValidBefore(final int position) {

        for (int i = 0; i < position; i++) {

            if ((gridButtons[i] instanceof ActivatedGridButton)
                    && gridButtons[i].getButtonValue() > currentRandomValue) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the position is valid after the current position, ensuring that the values placed
     * on the grid are in descending order after the given position.
     *
     * @param position the position to check
     * @return true if the position is valid, false otherwise
     */
    private boolean isPositionValidAfter(final int position) {

        for (int i = TOTAL_BUTTONS - 1; i > position; i--) {

            if ((gridButtons[i] instanceof ActivatedGridButton)
                    && gridButtons[i].getButtonValue() < currentRandomValue) {
                return false;
            }
        }

        return true;
    }

    /**
     * Counts how many placements (activated buttons) have been made on the grid.
     *
     * @return the number of successful placements
     */
    private int countPlacements() {

        int count = 0;

        for (GameGridButton button : gridButtons) {
            if (button instanceof ActivatedGridButton) {
                count++;
            }
        }

        return count;
    }

    /**
     * Displays an alert at the end of the game with options to try again or quit.
     *
     * @param title the title of the alert window
     * @param message the message to be displayed in the alert
     */

    private void showEndGameAlert(final String title,
                                  final String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(generateScoreSummary() + "\n\nWould you like to try again or quit?");

        ButtonType tryAgainButton = new ButtonType("Try Again");
        ButtonType quitButton = new ButtonType("Quit");

        alert.getButtonTypes().setAll(tryAgainButton, quitButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == tryAgainButton) {
                resetGameBoard();
            } else if (response == quitButton) {
                showFinalScoreAndExit();
            }
        });
    }


    /**
     * Generates a summary of the player's score, including games won, games lost, and successful placements.
     *
     * @return a string containing the score summary
     */
    private String generateScoreSummary() {
        double averagePlacements = (gamesPlayed > 0) ? (double) successfulPlacements / gamesPlayed : 0.0;
        int gamesLost = gamesPlayed - gamesWon;

        return String.format(
                "You won %d out of %d games and you lost %d out of %d games,\n" +
                        "with %d successful placements, an average of %.2f per game.",
                gamesWon, gamesPlayed, gamesLost, gamesPlayed, successfulPlacements, averagePlacements
        );
    }

    /**
     * Resets the game board by clearing the grid, initializing buttons, and preparing for a new game.
     */
    private void resetGameBoard() {
        for (int i = 0; i < TOTAL_BUTTONS; i++) {

            gridButtons[i] = new InactiveGridButton();
            gridButtons[i].setMaxWidth(Double.MAX_VALUE);
            gridButtons[i].setMaxHeight(Double.MAX_VALUE);

            int row = i / GRID_ROWS;
            int col = i % GRID_COLS;

            buttonGrid.getChildren().removeIf(node ->
                    GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col
            );

            buttonGrid.add(gridButtons[i], col, row);

            int position = i;
            gridButtons[i].setOnAction(event -> onButtonPressed(position, event));
        }

        validPositions.clear();
        prepareNextMove();
    }

    /**
     * Displays the final score and exits the game, closing the game window and showing a summary of the game results.
     */
    private void showFinalScoreAndExit() {
        Alert finalAlert = new Alert(Alert.AlertType.INFORMATION);
        finalAlert.setTitle("Final Score");
        finalAlert.setHeaderText("Thank you for playing!");
        finalAlert.setContentText(generateScoreSummary());

        finalAlert.showAndWait();
        closeGameWindow();
    }

    /**
     * Closes the game window and possibly returns to a main menu.
     */
    private void closeGameWindow() {
        // Assuming you want to close the game window and re-show the main menu
        gameMessage.getScene().getWindow().hide();

    }

    /**
     * Restarts the game by re-initializing the game controller and starting a new game session.
     */
    private void restartGame() {
        initialize();
    }
}
