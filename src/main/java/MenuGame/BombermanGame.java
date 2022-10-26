package MenuGame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import gamePlay.GamePlay;
import gamePlay.SoundGame;

enum STATUS {
    START, HELPMENU, HIGHSCORE, SETTINGS, BACK, MAIN, STOP, GAMEPLAY
}

enum MUSIC {
    ON, OFF
}

enum SOUND {
    ON, OFF
}



public class BombermanGame extends Application {
    public enum LEVEL {
        EASY, MEDIUM, HARD, NONE
    }
    private GamePlay game = new GamePlay();
    private SoundGame soundGame = new SoundGame();
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static Group root = new Group();
    public static STATUS status = STATUS.MAIN;
    public static LEVEL level = LEVEL.NONE;
    public static MUSIC music = MUSIC.ON;
    public static SOUND sound = SOUND.ON;

    @Override
    public void start(Stage theStage) {
        menu(theStage);
    }
    public void menu(Stage theStage) {
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
        SettingsLayer s = new SettingsLayer();
        game.start(theStage, theScene, gc);

        theStage.show();
        final Long[] startNanotime = {System.nanoTime()};
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
                    if(root.getChildren().contains(Menu.musicButton.circle)) {
                        root.getChildren().remove(Menu.musicButton.circle);
                        root.getChildren().remove(Menu.soundButton.circle);
                    }
                    m.renderButton(gc);
                    status = STATUS.STOP;
                }
                if (status.equals(STATUS.START)) {
                    clearScreen(gc);
                    l.createLevelButton();
                    l.renderLevelButton();
                    l.handleLevelButton(gc);
                    System.out.println(level);
                    status = STATUS.STOP;
                }
                if (status.equals(STATUS.SETTINGS)) {
                    clearScreen(gc);
                    s.renderButton();
                    status = STATUS.STOP;
                }
                if (status.equals(STATUS.GAMEPLAY)) {
                    clearScreen(gc);
                    root.getChildren().remove(Menu.backButton.circle);
                    root.getChildren().remove(LevelLayer.easyButton.rectangle);
                    root.getChildren().remove(LevelLayer.normalButton.rectangle);
                    root.getChildren().remove(LevelLayer.hardButton.rectangle);
                    //game.start(theStage, theScene, gc);
                    double time = 1.0* (currentTime - startNanotime[0]) / 1000000000;
                    startNanotime[0] = currentTime;
                    GamePlay.map.update(time, GamePlay.events);
                    GamePlay.map.render(gc);
                }
                if(music.equals(MUSIC.ON)) {
                    if(status == STATUS.GAMEPLAY) {
                        soundGame.playSound(GamePlay.map, GamePlay.events);
                    }
                    else {
                        soundGame.playSoundMenu();
                    }
                }
            }
        };

        time.start();
        if (status.equals(STATUS.STOP)) {
            System.out.println(1);
            time.stop();
        }
    }

    public static void clearScreen(GraphicsContext gc) {
        Color color = Color.rgb(0, 255, 0);
        gc.setFill(color);
        gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
}
