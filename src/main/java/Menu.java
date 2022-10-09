package main.java;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


public class Menu {
    public ArrayList<Button> circleButtons = new ArrayList<Button>();
    public static final Image buttonImage = new Image("Button/button.png");
    public static Button startButton = new Button(buttonImage, 20, 250, 210, 10, true);
    public static Button helpButton = new Button(buttonImage, 140, 15, 210, 245, true);
    public static Button highScoreButton = new Button(buttonImage, 260, -63, 210, 320, true);
    public static Button settingButton = new Button(buttonImage, 600, 450, 20, 245, true);
    public static Sprite playerBgImage1 = new Sprite(new Image("startLayer.png"), 0, 0, 10, 315, 400, 500);
    public static Sprite textBg = new Sprite(new Image("startLayer.png"), 0, -10, 50, 0, 687, 330);

    public void createCircleButton() {
        startButton.circle = new Circle(520, 400, 45);
        startButton.circle.setFill(new ImagePattern(startButton.cropImage()));
        BombermanGame.root.getChildren().add(startButton.circle);

        helpButton.circle = new Circle(520, 550, 45);
        helpButton.circle.setFill(new ImagePattern(helpButton.cropImage()));
        BombermanGame.root.getChildren().add(helpButton.circle);

        highScoreButton.circle = new Circle(670, 545, 45);
        highScoreButton.circle.setFill(new ImagePattern(highScoreButton.cropImage()));
        BombermanGame.root.getChildren().add(highScoreButton.circle);

        settingButton.circle = new Circle(670, 400, 45);
        settingButton.circle.setFill(new ImagePattern(settingButton.cropImage()));
        BombermanGame.root.getChildren().add(settingButton.circle);

        circleButtons.add(startButton);
        circleButtons.add(helpButton);
        circleButtons.add(highScoreButton);
        circleButtons.add(settingButton);
    }

    public void loadButton(GraphicsContext gc) {
        playerBgImage1.load();
        textBg.load();
        createCircleButton();
        handleButton(gc);
    }

    public void renderButton(GraphicsContext gc) {
        playerBgImage1.render(gc);
        textBg.render(gc);
    }

    public void handleButton(GraphicsContext gc) {
        for (int i = 0; i < circleButtons.size(); i++) {
            int index = i;

            circleButtons.get(index).circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    BombermanGame.status = STATUS.values()[index];
                    System.out.println(BombermanGame.status);
                }
            });

            circleButtons.get(index).circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    // Độ tương phản
                    colorAdjust.setContrast(0.3);
                    // Sắc độ
                    colorAdjust.setHue(-0.05);
                    // Độ sáng
                    colorAdjust.setBrightness(0.6);
                    // Độ bão hòa
                    colorAdjust.setSaturation(0.1);
                    circleButtons.get(index).circle.setEffect(colorAdjust);
                }
            });

            circleButtons.get(index).circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i = 0; i < circleButtons.size(); i++) {
                        circleButtons.get(i).circle.setEffect(null);
                    }
                }
            });
        }
    }
}
