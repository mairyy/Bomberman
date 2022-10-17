package main.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Status
enum STATUS {
    START, HELPMENU, HIGHSCORE, SETTINGS, BACK, MAIN, STOP
}

enum LEVEL {
    EASY, MEDIUM, HARD, NONE
}

public class BombermanGame extends Application {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static Group root = new Group();
    public static STATUS status = STATUS.MAIN;
    public static LEVEL level = LEVEL.NONE;

    public static void main(String[] agrs) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Bomberman");

        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearScreen(gc);

        Menu m = new Menu();
        m.loadButton();
        m.createCircleButton();
        m.renderButton(gc);
        m.handleButton(gc);
        HelpLayer h = new HelpLayer();
        h.load();
        LevelLayer l = new LevelLayer();

        theStage.show();
        AnimationTimer time = new AnimationTimer() {
            public void handle(long currentTime) {
                if (status.equals(STATUS.HELPMENU)) {
                    clearScreen(gc);
                    h.render(gc);
                    status = STATUS.STOP;
                }
                if (status.equals(STATUS.BACK)) {
                    clearScreen(gc);
                    root.getChildren().remove(Menu.backButton.circle);
                    if(root.getChildren().contains(LevelLayer.easyButton.rectangle)) {
                        root.getChildren().remove(LevelLayer.easyButton.rectangle);
                        root.getChildren().remove(LevelLayer.normalButton.rectangle);
                        root.getChildren().remove(LevelLayer.hardButton.rectangle);
                    }
                    m.renderButton(gc);
                    status = STATUS.STOP;
                }
                if (status.equals(STATUS.START)) {
                    clearScreen(gc);
                    l.createLevelButton();
                    l.renderLevelButton();
                    l.handleLevelButton(gc);
                    status = STATUS.STOP;
                }
            }
        };

        time.start();
        if (status.equals(STATUS.STOP)) time.stop();


    }

    public static void clearScreen(GraphicsContext gc) {
        Color color = Color.rgb(0, 255, 0);
        gc.setFill(color);
        gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
}
