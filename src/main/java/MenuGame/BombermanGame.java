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

import java.io.*;
import java.util.concurrent.TimeUnit;

enum STATUS {
    START, HELPMENU, HIGHSCORE, SETTINGS, BACK, RESTART, NEXT, HOME, PAUSE, STOP, GAMEPLAY
}

enum STATUSGAME {
    PLAY, WIN, LOSE, NONE
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

    public static int number = 1;
    public static GamePlay game = new GamePlay(number);
    private SoundGame soundGame = new SoundGame();
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static Group root = new Group();
    public static STATUS status = STATUS.HOME;
    public static LEVEL level = LEVEL.NONE;
    public static MUSIC music = MUSIC.ON;
    public static SOUND sound = SOUND.ON;
    public static STATUSGAME statusgame = STATUSGAME.NONE;

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

        MenuControl menu = new MenuControl(gc, this);

        theStage.show();
        final Long[] startNanotime = {System.nanoTime()};
        AnimationTimer time = new AnimationTimer() {
            public void handle(long currentTime) {
                if (status.equals(STATUS.HELPMENU)) {
                    statusgame = STATUSGAME.NONE;
                    clearScreen(gc);
                    menu.helpLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.BACK)) {
                    statusgame = STATUSGAME.NONE;
                    clearScreen(gc);
                    root.getChildren().remove(menu.backButton.circle);
                    if (root.getChildren().contains(menu.levelLayer.easyButton.rectangle)) {
                        menu.levelLayer.clear();
                    }
                    if (root.getChildren().contains(menu.musicButton.circle)) {
                        menu.settingsLayer.clear();
                    }
                    if (root.getChildren().contains(menu.restartButton.circle)) {
                        menu.highScoreLayer.clear();
                    }
                    menu.homeLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.START)) {
                    statusgame = STATUSGAME.NONE;
                    if (root.getChildren().contains(menu.restartButton.circle)) {
                        status = STATUS.GAMEPLAY;
                    } else {
                        clearScreen(gc);
                        menu.levelLayer.render(gc);
                        System.out.println(level);
                        status = STATUS.STOP;
                    }
                }

                if (status.equals(STATUS.SETTINGS)) {
                    statusgame = STATUSGAME.NONE;
                    clearScreen(gc);
                    menu.settingsLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.GAMEPLAY)) {
                    statusgame = STATUSGAME.PLAY;
                    menu.highScoreLayer.isHighScoreLayer = false;
                    clearScreen(gc);
                    root.getChildren().remove(menu.backButton.circle);
                    menu.levelLayer.clear();
                    if (root.getChildren().contains(menu.restartButton.circle)) {
                        menu.pauseLayer.clear();
                        if (root.getChildren().contains(menu.nextButton.circle)) {
                            root.getChildren().remove(menu.nextButton.circle);
                        }
                    }

                    double time = 1.0 * (currentTime - startNanotime[0]) / 1000000000;
                    startNanotime[0] = currentTime;
                    game.start(theStage, theScene, gc);
                    game.map.update(time, game.events);
                    game.map.render(gc);
                    menu.scoreBar.render(gc);
                    if (game.isEnd) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(game.score + " " + game.timeGame);
                        clearScreen(gc);
                        if (!game.map.player.isDestroy()) {
                            statusgame = STATUSGAME.WIN;
                            if(music.equals(MUSIC.ON)) {
                                soundGame.playSoundWin();
                            }
                            root.getChildren().remove(menu.pauseButton.circle);
                            menu.highScoreLayer.writeRecord();
                            menu.highScoreLayer.load();
                            menu.winLayer.render(gc);
                        } else {
                            statusgame = STATUSGAME.LOSE;
                            if(music.equals(music.ON)) {
                                soundGame.playSoundLose();
                            }
                            root.getChildren().remove(menu.pauseButton.circle);
                            menu.gameOver.render(gc);
                        }
                        game.isEnd = false;
                        status = STATUS.STOP;
                    }
                }

                if (status.equals(STATUS.PAUSE)) {
                    statusgame = STATUSGAME.NONE;
                    root.getChildren().remove(menu.pauseButton.circle);
                    menu.pauseLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.HOME)) {
                    statusgame = STATUSGAME.NONE;
                    clearScreen(gc);
                    game = new GamePlay(number);
                    game.start(theStage, theScene, gc);
                    if (root.getChildren().contains(menu.nextButton.circle)) {
                        root.getChildren().remove(menu.nextButton.circle);
                    }
                    menu.pauseLayer.clear();
                    menu.homeLayer.render(gc);
                    status = STATUS.STOP;
                }

<<<<<<< HEAD
                if(status.equals(STATUS.RESTART)) {
                    statusgame = STATUSGAME.NONE;
=======
                if (status.equals(STATUS.RESTART)) {
>>>>>>> b82524dbcbfdac1ca54a31bdb17dc9b785d2c3a9
                    if (menu.highScoreLayer.isHighScoreLayer) {
                        menu.highScoreLayer.resetRecord();
                        menu.highScoreLayer.load();
                        status = STATUS.HIGHSCORE;
                    } else {
                        game = new GamePlay(number);
                        game.start(theStage, theScene, gc);
                        status = STATUS.GAMEPLAY;
                    }
                }

                if (status.equals(STATUS.NEXT)) {
                    if (level.equals(LEVEL.EASY)) {
                        level = LEVEL.MEDIUM;
                        number = 2;
                        game = new GamePlay(number);
                    } else if (level.equals(LEVEL.MEDIUM)) {
                        level = LEVEL.HARD;
                        number = 3;
                        game = new GamePlay(number);
                    } else if (level.equals(LEVEL.HARD)) {
                        level = LEVEL.EASY;
                        number = 1;
                        game = new GamePlay(number);
                    }
                    status = STATUS.GAMEPLAY;
                }

                if (status.equals(STATUS.HIGHSCORE)) {
                    statusgame = STATUSGAME.NONE;
                    clearScreen(gc);
                    try {
                        if (root.getChildren().contains(menu.highScoreButton.circle)) {
                            menu.homeLayer.clear();
                        }
                        menu.highScoreLayer.render(gc);
                        status = STATUS.STOP;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

<<<<<<< HEAD
                if(music.equals(MUSIC.ON) && statusgame.equals(STATUSGAME.NONE)) {
=======
                if (music.equals(MUSIC.ON) && !status.equals(STATUS.GAMEPLAY)) {
>>>>>>> b82524dbcbfdac1ca54a31bdb17dc9b785d2c3a9
                    soundGame.playSoundMenu();
                } else {
                    soundGame.closeMenu();
                }
<<<<<<< HEAD
                if(sound.equals(SOUND.ON) && !statusgame.equals(STATUSGAME.NONE)) {
=======
                if (sound.equals(SOUND.ON) && status.equals(STATUS.GAMEPLAY)) {
>>>>>>> b82524dbcbfdac1ca54a31bdb17dc9b785d2c3a9
                    soundGame.playSound(game.map, game.events);
                } else {
                    soundGame.close();
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