package MenuGame;

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
    public static Button backButton = new Button(buttonImage, 700, 100, 20, 90, true);
    public static Button musicButton = new Button(buttonImage, 500, 100, 310, 320, true);
    public static Button soundButton = new Button(buttonImage, 500, 100, 120, 245, true);
    public static Button restartButton = new Button(buttonImage, 500, 100, 120, 168, true);
    public static Button nextButton = new Button(buttonImage, 500, 100, 120, 90, true);
    public static Button homeButton = new Button(buttonImage, 500, 100, 310, 10, true);
    public static Sprite playerBgImage1 = new Sprite(new Image("startLayer.png"), 0, 0, 10, 315, 400, 500);
    public static Sprite textBg = new Sprite(new Image("startLayer.png"), 0, -10, 50, 0, 687, 330);

    public void createCircleButton() {
        startButton.circle = new Circle(520, 400, 45);
        startButton.circle.setFill(new ImagePattern(startButton.cropImage()));

        helpButton.circle = new Circle(520, 550, 45);
        helpButton.circle.setFill(new ImagePattern(helpButton.cropImage()));

        highScoreButton.circle = new Circle(670, 545, 45);
        highScoreButton.circle.setFill(new ImagePattern(highScoreButton.cropImage()));

        settingButton.circle = new Circle(670, 400, 45);
        settingButton.circle.setFill(new ImagePattern(settingButton.cropImage()));

        backButton.circle = new Circle(70, 740, 45);
        backButton.circle.setFill(new ImagePattern(backButton.cropImage()));

        musicButton.circle = new Circle(320, 355, 45);
        musicButton.circle.setFill(new ImagePattern(musicButton.cropImage()));

        soundButton.circle = new Circle(480, 360, 45);
        soundButton.circle.setFill(new ImagePattern(soundButton.cropImage()));

        restartButton.circle = new Circle(300, 700, 45);
        restartButton.circle.setFill(new ImagePattern(restartButton.cropImage()));

        nextButton.circle = new Circle(400, 700, 45);
        nextButton.circle.setFill(new ImagePattern(nextButton.cropImage()));

        homeButton.circle = new Circle(500, 700, 45);
        homeButton.circle.setFill(new ImagePattern(homeButton.cropImage()));

        circleButtons.add(startButton);
        circleButtons.add(helpButton);
        circleButtons.add(highScoreButton);
        circleButtons.add(settingButton);
        circleButtons.add(backButton);
        circleButtons.add(restartButton);
        circleButtons.add(nextButton);
        circleButtons.add(homeButton);
        circleButtons.add(musicButton);
        circleButtons.add(soundButton);
    }

    public void loadButton() {
        playerBgImage1.load();
        textBg.load();
    }

    public void renderButton(GraphicsContext gc) {
        playerBgImage1.render(gc);
        textBg.render(gc);
        BombermanGame.root.getChildren().add(startButton.circle);
        BombermanGame.root.getChildren().add(helpButton.circle);
        BombermanGame.root.getChildren().add(settingButton.circle);
        BombermanGame.root.getChildren().add(highScoreButton.circle);
    }

    public void handleButton(GraphicsContext gc) {
        for (int i = 0; i < circleButtons.size(); i++) {
            int index = i;

            circleButtons.get(index).circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // index < 8 là các nút ở màn hình chính và nút back
                    if (index < 8) BombermanGame.status = STATUS.values()[index];
                    // index = 8 là nút music, index = 9 là nút sound
                    else if (index == 7) {
                        if (BombermanGame.music.equals(MUSIC.OFF)) {
                            circleButtons.get(index).image = buttonImage;
                            circleButtons.get(index).circle.setFill(new ImagePattern(circleButtons.get(index).cropImage()));
                            BombermanGame.music = MUSIC.ON;
                        } else {
                            circleButtons.get(index).image = new Image("Button/button (1).png");
                            circleButtons.get(index).circle.setFill(new ImagePattern(circleButtons.get(index).cropImage()));
                            BombermanGame.music = MUSIC.OFF;
                        }
                    } else {
                        if (BombermanGame.sound.equals(SOUND.OFF)) {
                            circleButtons.get(index).image = buttonImage;
                            circleButtons.get(index).circle.setFill(new ImagePattern(circleButtons.get(index).cropImage()));
                            BombermanGame.sound = SOUND.ON;
                        } else {
                            circleButtons.get(index).image = new Image("Button/button (1).png");
                            circleButtons.get(index).circle.setFill(new ImagePattern(circleButtons.get(index).cropImage()));
                            BombermanGame.sound = SOUND.OFF;
                        }
                    }
                    System.out.println("status " + BombermanGame.status);
                    System.out.println("level " + BombermanGame.level);
                    System.out.println("music " + BombermanGame.music);
                    System.out.println("sound " + BombermanGame.sound);
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
                    circleButtons.get(index).circle.setEffect(null);
                }
            });
        }
    }
}
