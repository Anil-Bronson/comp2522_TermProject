import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The NumberGame class is the main entry point for launching the Number Game application.
 * It extends the JavaFX Application class to set up the main game window, load the FXML layout,
 * and display the game scene. This class is responsible for initializing the application and
 * setting up the primary stage for the game interface.
 */
public class NumberGame extends Application {

    /**
     * Starts the Number Game application by launching the JavaFX application.
     *
     * This method serves as the entry point to the game and triggers the
     * start() method to load the user interface and display the game window.
     */
    public static void startNumberGame() {
        launch();
    }

    /**
     * Initializes the primary stage and loads the FXML layout for the game interface.
     * It sets up the scene with the appropriate dimensions and displays the game window.
     *
     * @param primaryStage the main stage of the JavaFX application
     * @throws Exception if an error occurs while loading the FXML file or setting up the scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("numberGame/GameLayout.fxml"));
        Scene scene = new Scene(loader.load(), 700, 450);
        primaryStage.setTitle("Number Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
