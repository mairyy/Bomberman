package MenuGame;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class LevelLayer {
    public static Image levelImg = new Image("level.png");
    public static Button easyButton = new Button(levelImg, 400, 100, 140, 260, false);
    public static Button normalButton = new Button(levelImg, 400, 100, 140, 350, false);
    public static Button hardButton = new Button(levelImg, 400, 100, 140, 440, false);
    public static ArrayList<Button> rectangleButtons = new ArrayList<Button>();

    public void createLevelButton() {
        easyButton.rectangle = new Rectangle(295, 200, easyButton.width, easyButton.height);
        easyButton.rectangle.setFill(new ImagePattern(easyButton.cropImage()));

        normalButton.rectangle = new Rectangle(295, 350, normalButton.width, normalButton.height);
        normalButton.rectangle.setFill(new ImagePattern(normalButton.cropImage()));

        hardButton.rectangle = new Rectangle(295, 500, hardButton.width, hardButton.height);
        hardButton.rectangle.setFill(new ImagePattern(hardButton.cropImage()));

        rectangleButtons.add(easyButton);
        rectangleButtons.add(normalButton);
        rectangleButtons.add(hardButton);
    }

    public void renderLevelButton() {
        BombermanGame.root.getChildren().remove(MenuControl.helpButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.settingButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.highScoreButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.startButton.circle);
        BombermanGame.root.getChildren().add(easyButton.rectangle);
        BombermanGame.root.getChildren().add(normalButton.rectangle);
        BombermanGame.root.getChildren().add(hardButton.rectangle);
        BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
    }

    public void handleLevelButton(GraphicsContext gc) {
        for (int i = 0; i < rectangleButtons.size(); i++) {
            int index = i;

            rectangleButtons.get(index).rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    BombermanGame.level = BombermanGame.LEVEL.values()[index];
                    BombermanGame.status = STATUS.GAMEPLAY;
                    System.out.println(BombermanGame.level);
                }
            });

            rectangleButtons.get(index).rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    // Độ tương phản
                    colorAdjust.setContrast(0.3);
                    // Sắc độ
                    colorAdjust.setHue(-0.05);
                    // Độ sáng
                    colorAdjust.setBrightness(0.4);
                    // Độ bão hòa
                    colorAdjust.setSaturation(0.1);
                    rectangleButtons.get(index).rectangle.setEffect(colorAdjust);
                }
            });

            rectangleButtons.get(index).rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    rectangleButtons.get(index).rectangle.setEffect(null);
                }
            });
        }
    }

    public static void clear() {
        BombermanGame.root.getChildren().remove(easyButton.rectangle);
        BombermanGame.root.getChildren().remove(normalButton.rectangle);
        BombermanGame.root.getChildren().remove(hardButton.rectangle);
    }
}
