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
    public int number = 1;
    public GamePlay game = new GamePlay(number);
    private SoundGame soundGame = new SoundGame();
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static Group root = new Group();
    public static STATUS status = STATUS.HOME;
    public static LEVEL level = LEVEL.NONE;
    public static MUSIC music = MUSIC.OFF;
    public static SOUND sound = SOUND.ON;
    public static double timeEasy;
    public static double timeMedium;
    public static double timeHard;
    public static int scoreEasy;
    public static int scoreMedium;
    public static int scoreHard;


    @Override
    public void start(Stage theStage) {
        menu(theStage);
        readRecord();
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
                        menu.settingsLayer.clear();
                    }
                    if(root.getChildren().contains(menu.restartButton.circle)) {
                        menu.highScoreLayer.clear();
                    }
                    menu.homeLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.START)) {
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
                    clearScreen(gc);
                    menu.settingsLayer.render(gc);
                    status = STATUS.STOP;
                }

                if (status.equals(STATUS.GAMEPLAY)) {
                    clearScreen(gc);
                    root.getChildren().remove(menu.backButton.circle);
                    menu.levelLayer.clear();
                    if (root.getChildren().contains(menu.restartButton.circle)) {
                        menu.pauseLayer.clear();
                        if (root.getChildren().contains(menu.nextButton.circle)) {
                            root.getChildren().remove(menu.nextButton.circle);
                        }
                    }

                    double time = 1.0* (currentTime - startNanotime[0]) / 1000000000;
                    startNanotime[0] = currentTime;
                    game.start(theStage, theScene, gc);
                    game.map.update(time, game.events);
                    game.map.render(gc);
                    if (!root.getChildren().contains(menu.pauseButton.circle)) {
                        root.getChildren().add(menu.pauseButton.circle);
                    }
                    if (game.isEnd) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(game.score + " " + game.timeGame);
                        clearScreen(gc);
                        if (!game.map.player.isDestroy()) {
                            root.getChildren().remove(menu.pauseButton.circle);
                            handleRecord();
                            menu.winLayer.render(gc);
                        } else {
                            root.getChildren().remove(menu.pauseButton.circle);
                            menu.gameOver.render(gc);
                        }
                        game.isEnd = false;
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
                    game = new GamePlay(number);
                    game.start(theStage, theScene, gc);
                    if (root.getChildren().contains(menu.nextButton.circle)) {
                        root.getChildren().remove(menu.nextButton.circle);
                    }
                    menu.pauseLayer.clear();
                    menu.homeLayer.render(gc);
                    status = STATUS.STOP;
                }

                if(status.equals(STATUS.RESTART)) {
                    game = new GamePlay(number);
                    game.start(theStage, theScene, gc);
                    status = STATUS.GAMEPLAY;
                }

                if(status.equals(STATUS.NEXT)) {
                    if(level.equals(LEVEL.EASY)) {
                        level = LEVEL.MEDIUM;
                        number = 2;
                        game = new GamePlay(number);
                    } else if(level.equals(LEVEL.MEDIUM)) {
                        level = LEVEL.HARD;
                        number = 3;
                        game = new GamePlay(number);
                    }  else if(level.equals(LEVEL.HARD)) {
                        level = LEVEL.EASY;
                        number = 1;
                        game = new GamePlay(number);
                    }
                    status = STATUS.GAMEPLAY;
                }

                if (status.equals(STATUS.HIGHSCORE)) {
                    clearScreen(gc);
                    try {
                        menu.homeLayer.clear();
                        menu.highScoreLayer.render(gc);
                        status = STATUS.STOP;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                if(music.equals(MUSIC.ON) && !status.equals(STATUS.GAMEPLAY)) {
                    soundGame.playSoundMenu();
                } else {
                    soundGame.closeMenu();
                }
                if(sound.equals(SOUND.ON) && status.equals(STATUS.GAMEPLAY)) {
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

    public void readRecord() {
        File file = new File("res/resource/map/record.txt");
        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))){
            String line = inputStream.readLine();
            String[] arr = line.split(" ");
            scoreEasy = Integer.parseInt(arr[0]);
            timeEasy = Double.parseDouble(arr[1]);
            scoreEasy = Integer.parseInt(arr[2]);
            timeEasy = Double.parseDouble(arr[3]);
            scoreEasy = Integer.parseInt(arr[4]);
            timeEasy = Double.parseDouble(arr[5]);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleRecord() {
        switch (level) {
            case EASY:
                if(scoreEasy < game.score || (scoreEasy == game.score && timeEasy > game.timeGame)) {
                    scoreEasy = game.score;
                    timeEasy = game.timeGame;
                }
                break;
            case MEDIUM:
                if(scoreMedium < game.score || (scoreMedium == game.score && timeMedium > game.timeGame)) {
                    scoreMedium = game.score;
                    timeMedium = game.timeGame;
                }
                break;
            case HARD:
                if(scoreHard < game.score || (scoreHard == game.score && timeHard > game.timeGame)) {
                    scoreHard = game.score;
                    timeHard = game.timeGame;
                }
                break;
        }
        try {
            FileWriter fw = new FileWriter("res/resource/map/record.txt");
            String s = scoreEasy + " " + timeEasy + " " + scoreMedium + " " + timeMedium
                    + " " + scoreHard + " " + timeHard;
            fw.write(s);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
