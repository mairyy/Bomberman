package MenuGame;

import MenuGame.Button.Button;
import gamePlay.GamePlay;
import gamePlay.entity.Bom;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;

public class LevelLayer extends Menu {
    public BombermanGame bombermanGame;
    public static Image levelImg = new Image("level.png");
    public static Button easyButton = new Button(levelImg, 400, 100, 140, 260, false);
    public static Button normalButton = new Button(levelImg, 400, 100, 140, 350, false);
    public static Button hardButton = new Button(levelImg, 400, 100, 140, 440, false);
    public static ArrayList<Button> rectangleButtons = new ArrayList<Button>();


    public void load() {
        easyButton.rectangle = new Rectangle(295, 200, easyButton.getWidth(), easyButton.getHeight());
        easyButton.rectangle.setFill(new ImagePattern(easyButton.cropImage()));

        normalButton.rectangle = new Rectangle(295, 350, normalButton.getWidth(), normalButton.getHeight());
        normalButton.rectangle.setFill(new ImagePattern(normalButton.cropImage()));

        hardButton.rectangle = new Rectangle(295, 500, hardButton.getWidth(), hardButton.getHeight());
        hardButton.rectangle.setFill(new ImagePattern(hardButton.cropImage()));

        rectangleButtons.add(easyButton);
        rectangleButtons.add(normalButton);
        rectangleButtons.add(hardButton);
    }

    public void render(GraphicsContext gc) {
        BombermanGame.root.getChildren().remove(MenuControl.helpButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.settingButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.highScoreButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.startButton.circle);
        BombermanGame.root.getChildren().add(easyButton.rectangle);
        BombermanGame.root.getChildren().add(normalButton.rectangle);
        BombermanGame.root.getChildren().add(hardButton.rectangle);
        BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
    }

    public void handleLevelButton(GraphicsContext gc, BombermanGame bombermanGame) {
        for (int i = 0; i < rectangleButtons.size(); i++) {
            int index = i;

            rectangleButtons.get(index).rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    bombermanGame.level = BombermanGame.LEVEL.values()[index];
                    bombermanGame.status = STATUS.GAMEPLAY;
                    if (bombermanGame.level.equals(BombermanGame.LEVEL.EASY)) {
                        bombermanGame.game = new GamePlay(1);
                        bombermanGame.number = 1;
                    }
                    if (bombermanGame.level.equals(BombermanGame.LEVEL.MEDIUM)) {
                        bombermanGame.game = new GamePlay(2);
                        bombermanGame.number = 2;
                    }
                    if (bombermanGame.level.equals(BombermanGame.LEVEL.HARD)) {
                        bombermanGame.game = new GamePlay(3);
                        bombermanGame.number = 3;
                    }
                    System.out.println(BombermanGame.level);
                }
            });

            rectangleButtons.get(index).rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    // ????? t????ng ph???n
                    colorAdjust.setContrast(0.3);
                    // S???c ?????
                    colorAdjust.setHue(-0.05);
                    // ????? s??ng
                    colorAdjust.setBrightness(0.4);
                    // ????? b??o h??a
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

    public void clear() {
        BombermanGame.root.getChildren().remove(easyButton.rectangle);
        BombermanGame.root.getChildren().remove(normalButton.rectangle);
        BombermanGame.root.getChildren().remove(hardButton.rectangle);
    }
}
