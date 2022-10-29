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

import java.util.concurrent.TimeUnit;

enum STATUS {
    START, HELPMENU, HIGHSCORE, SETTINGS, BACK, RESTART, NEXT, HOME, PAUSE, STOP, GAMEPLAY
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
    public static STATUS status = STATUS.HOME;
    public static LEVEL level = LEVEL.NONE;
    public static MUSIC music = MUSIC.OFF;
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

        MenuControl menu = new MenuControl(gc);
        game.start(theStage, theScene, gc);

        theStage.show();
        final Long[] startNanotime = {System.nanoTime()};
        AnimationTimer time = new AnimationTimer() {
            public void handle(long currentTime) {
                if (status.equals(STATUS.HELPMENU)) {
                    clearScreen(gc);
                    menu.helpLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.BACK)) {
                    clearScreen(gc);
                    root.getChildren().remove(menu.backButton.circle);
                    if(root.getChildren().contains(menu.levelLayer.easyButton.rectangle)) {
                        menu.levelLayer.clear();
                    }
                    if(root.getChildren().contains(menu.musicButton.circle)) {
                        root.getChildren().remove(menu.musicButton.circle);
                        root.getChildren().remove(menu.soundButton.circle);
                    }
                    menu.homeLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.START)) {
                    if (root.getChildren().contains(menu.restartButton.circle)) {
                        status = STATUS.GAMEPLAY;
                    } else {
                        clearScreen(gc);
                        menu.levelLayer.renderLevelButton();
                        System.out.println(level);
                        status = STATUS.STOP;
                    }
                }

                if (status.equals(STATUS.SETTINGS)) {
                    clearScreen(gc);
                    menu.settingsLayer.renderButton();
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.GAMEPLAY)) {
                    clearScreen(gc);
                    root.getChildren().remove(menu.backButton.circle);
                    menu.levelLayer.clear();
                    if (root.getChildren().contains(menu.restartButton.circle)) {
                        menu.pauseLayer.clear();
                    }

                    double time = 1.0* (currentTime - startNanotime[0]) / 1000000000;
                    startNanotime[0] = currentTime;
                    GamePlay.map.update(time, GamePlay.events);
                    GamePlay.map.render(gc);
                    if (!root.getChildren().contains(menu.pauseButton.circle)) {
                        root.getChildren().add(menu.pauseButton.circle);
                    }
                    if (GamePlay.isEnd) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(game.score + " " + game.timeGame);
                        clearScreen(gc);
                        if (!GamePlay.map.player.isDestroy()) {
                            root.getChildren().remove(menu.pauseButton.circle);
                            menu.winLayer.render(gc);
                        } else {
                            root.getChildren().remove(menu.pauseButton.circle);
                            menu.gameOver.render(gc);
                        }
                        GamePlay.isEnd = false;
                        status = STATUS.STOP;
                    }
                }

                if (status.equals(STATUS.PAUSE)) {
                    root.getChildren().remove(menu.pauseButton.circle);
                    menu.pauseLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.HOME)) {
                    clearScreen(gc);
                    if (root.getChildren().contains(menu.nextButton.circle)) {
                        root.getChildren().remove(menu.nextButton.circle);
                    }
                    menu.pauseLayer.clear();
                    menu.homeLayer.render(gc);
                    status = STATUS.STOP;
                }

                if(status.equals(STATUS.RESTART)) {
                    System.out.println("restart");
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

    private static void clearScreen(GraphicsContext gc) {
        Color color = Color.rgb(0, 255, 0);
        gc.setFill(color);
        gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

}
