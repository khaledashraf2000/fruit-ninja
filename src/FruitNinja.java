import controller.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class FruitNinja extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game.play();
    }
}
