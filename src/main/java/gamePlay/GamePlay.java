package main.java.gamePlay;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import main.java.gamePlay.Map;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GamePlay extends Application {
    public final static int widthUnit =  50;
    public static int widthScreen;
    public static int heightScreen;

    @Override
    public void start(Stage stage)  {
        stage.setTitle("Bomberman");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        File file = new File("res/resource/map/map1.txt");
        Map map = new Map();
        map.loadMap(file);
        SoundGame soundGame = new SoundGame();
        widthScreen = map.getLenWidth()*widthUnit;
        heightScreen = map.getLenHeight()*widthUnit;
        Canvas canvas = new Canvas(widthScreen, heightScreen);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //handle event.
        List<KeyCode> events = new ArrayList<>();
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if(!events.contains(keyEvent.getCode())) {
                            events.add(keyEvent.getCode());
                        }
                    }
                }
        );
        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        events.remove(keyEvent.getCode());
                    }
                }
        );
        final Long[] startNanotime = {System.nanoTime()};
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                double time = 1.0* (l - startNanotime[0]) / 1000000000;
                startNanotime[0] = l;
                map.update(time, events);
                soundGame.playSound(map, events);
                map.render(gc);
            }
        }.start();

        stage.show();
    }

}