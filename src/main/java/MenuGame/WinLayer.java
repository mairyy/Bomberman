package MenuGame;

import MenuGame.Button.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WinLayer {
    public static Sprite winImage = new Sprite(new Image("winLayer.png"), 0, 0, 0, 0);

    public static void load() {
        winImage.load();
    }

    public static void render(GraphicsContext gc) {
        winImage.render(gc);
        MenuControl.restartButton.circle.setCenterX(300);
        MenuControl.restartButton.circle.setCenterY(700);
        MenuControl.homeButton.circle.setCenterX(500);
        MenuControl.homeButton.circle.setCenterY(700);
        BombermanGame.root.getChildren().add(MenuControl.restartButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.nextButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.homeButton.circle);
    }
}
