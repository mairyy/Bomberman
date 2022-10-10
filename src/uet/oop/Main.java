package uet.oop;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public final static int widthUnit =  50;
    public static int widthScreen;
    public static int heightScreen;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Bomberman");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        File file = new File("src/main/resource/map/map1.txt");
        Map map = new Map();
        map.loadMap(file);
        widthScreen = map.getLenWidth()*widthUnit;
        heightScreen = map.getLenHeight()*widthUnit;
        Canvas canvas = new Canvas(widthScreen, heightScreen);
        Rectangle2D abc = new Rectangle();
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
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                map.handleInput(events);
                map.render(gc);
            }
        }.start();

        map.render(gc);

        stage.show();
    }

}