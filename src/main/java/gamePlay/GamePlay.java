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

public class GamePlay {
    public final static int widthUnit =  50;
    public static Map map = new Map();
    public static List<KeyCode> events = new ArrayList<>();

    public void start(Stage stage, Scene scene, GraphicsContext gc)  {
        File file = new File("res/resource/map/map1.txt");
        map.loadMap(file);

        //handle key event.
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

        stage.show();
    }

}