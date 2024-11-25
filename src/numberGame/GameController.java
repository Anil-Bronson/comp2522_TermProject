import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    @FXML
    private Label gameMessage;
    @FXML
    private GridPane buttonGrid;
    @FXML
    private GameGridButton[] gridButtons;

    private ArrayList<Integer> validPositions;
    private int currentRandomValue;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int successfulPlacements = 0;

    private static final int TOTAL_BUTTONS = 20;
    private static final int GRID_ROWS = 5;
    private static final int GRID_COLS = 5;

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

    private void onButtonPressed(int position, ActionEvent event) {
        if (gridButtons[position].isButtonValid(position, validPositions)) {

            ActivatedGridButton activatedButton = new ActivatedGridButton(currentRandomValue);
            gridButtons[position] = activatedButton;
            replaceButton(position, activatedButton);
            prepareNextMove();

        }
    }

    private void replaceButton(final int position, final ActivatedGridButton activatedButton) {
        int row = position / GRID_ROWS;
        int col = position % GRID_COLS;

        buttonGrid.getChildren().removeIf(node ->
                GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col
        );

        buttonGrid.add(activatedButton, col, row);
    }

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

    private boolean checkForVictory() {

        for (GameGridButton button : gridButtons) {

            if (!(button instanceof ActivatedGridButton)) {
                return false;
            }
        }

        return true;
    }

    private void generateRandomValue() {

        Random random = new Random();
        currentRandomValue = random.nextInt(1000) + 1;

    }

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
    private boolean isPositionValidBefore(final int position) {

        for (int i = 0; i < position; i++) {

            if ((gridButtons[i] instanceof ActivatedGridButton)
                    && gridButtons[i].getButtonValue() > currentRandomValue) {
                return false;
            }
        }

        return true;
    }

    private boolean isPositionValidAfter(final int position) {

        for (int i = TOTAL_BUTTONS - 1; i > position; i--) {

            if ((gridButtons[i] instanceof ActivatedGridButton)
                    && gridButtons[i].getButtonValue() < currentRandomValue) {
                return false;
            }
        }

        return true;
    }

    private int countPlacements() {

        int count = 0;

        for (GameGridButton button : gridButtons) {
            if (button instanceof ActivatedGridButton) {
                count++;
            }
        }

        return count;
    }

    private void showEndGameAlert(String title, String message) {
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


    private String generateScoreSummary() {
        double averagePlacements = (gamesPlayed > 0) ? (double) successfulPlacements / gamesPlayed : 0.0;
        int gamesLost = gamesPlayed - gamesWon;

        return String.format(
                "You won %d out of %d games and you lost %d out of %d games,\n" +
                        "with %d successful placements, an average of %.2f per game.",
                gamesWon, gamesPlayed, gamesLost, gamesPlayed, successfulPlacements, averagePlacements
        );
    }

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

    private void showFinalScoreAndExit() {
        Alert finalAlert = new Alert(Alert.AlertType.INFORMATION);
        finalAlert.setTitle("Final Score");
        finalAlert.setHeaderText("Thank you for playing!");
        finalAlert.setContentText(generateScoreSummary());

        finalAlert.showAndWait();
        closeGameWindow();
    }

    private void closeGameWindow() {
        // Assuming you want to close the game window and re-show the main menu
        gameMessage.getScene().getWindow().hide();

    }

    private void restartGame() {
        initialize();
    }
}
