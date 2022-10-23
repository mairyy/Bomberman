package main.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HelpLayer {
    public static Sprite helpLayerImage = new Sprite(new Image("helpLayer.png"), 0, 0, 0, 0, 800, 800);

    public void load() {
        helpLayerImage.load();
    }

    public void render(GraphicsContext gc) {
        BombermanGame.root.getChildren().remove(Menu.helpButton.circle);
        BombermanGame.root.getChildren().remove(Menu.settingButton.circle);
        BombermanGame.root.getChildren().remove(Menu.highScoreButton.circle);
        BombermanGame.root.getChildren().remove(Menu.startButton.circle);
        helpLayerImage.render(gc);
        BombermanGame.root.getChildren().add(Menu.backButton.circle);
    }
}
