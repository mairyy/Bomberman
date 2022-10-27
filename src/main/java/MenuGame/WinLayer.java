package MenuGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WinLayer {
    public static Sprite winImage = new Sprite(new Image("winLayer.png"), 0, 0, 0, 0);

    public static void load() {
        winImage.load();
    }

    public static void render(GraphicsContext gc) {
        winImage.render(gc);
        BombermanGame.root.getChildren().add(Menu.restartButton.circle);
        BombermanGame.root.getChildren().add(Menu.nextButton.circle);
        BombermanGame.root.getChildren().add(Menu.homeButton.circle);
    }
}
