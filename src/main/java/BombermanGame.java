package main.java;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class BombermanGame extends Application {
    public static void main(String[] agrs) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Bomberman");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        theStage.show();
    }
}
