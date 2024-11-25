import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NumberGame extends Application {
    public static void startNumberGame() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("numberGame/GameLayout.fxml"));
        Scene scene = new Scene(loader.load(), 696, 420);
        primaryStage.setTitle("Number Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

