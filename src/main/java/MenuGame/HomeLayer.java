package MenuGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HomeLayer {
    public static Sprite playerBgImage1 = new Sprite(new Image("startLayer.png"), 0, 0, 10, 315, 400, 500);
    public static Sprite textBg = new Sprite(new Image("startLayer.png"), 0, -10, 50, 0, 687, 330);

    public void load() {
        playerBgImage1.load();
        textBg.load();
    }

    public void render(GraphicsContext gc) {
        playerBgImage1.render(gc);
        textBg.render(gc);
        MenuControl.startButton.circle.setCenterX(520);
        MenuControl.startButton.circle.setCenterY(400);
        if (!BombermanGame.root.getChildren().contains(MenuControl.startButton.circle)) {
            BombermanGame.root.getChildren().add(MenuControl.startButton.circle);
        }
        BombermanGame.root.getChildren().add(MenuControl.helpButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.settingButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.highScoreButton.circle);
    }
}
