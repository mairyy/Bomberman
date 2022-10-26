import javafx.application.Application;
import javafx.stage.Stage;
import MenuGame.BombermanGame;

public class Main extends Application {
    private BombermanGame bombermanGame = new BombermanGame();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        bombermanGame.start(theStage);
    }

}
