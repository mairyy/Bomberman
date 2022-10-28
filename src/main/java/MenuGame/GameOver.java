package MenuGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameOver {
    public static Sprite gameOverImg = new Sprite(new Image("loseLayer.png"), 0, 0, 0, 0);

    public static void load() {
        gameOverImg.load();
    }

    public static void render(GraphicsContext gc) {
        gameOverImg.render(gc);
        Menu.restartButton.circle.setCenterX(350);
        Menu.restartButton.circle.setCenterY(650);
        Menu.homeButton.circle.setCenterX(450);
        Menu.homeButton.circle.setCenterY(650);
        BombermanGame.root.getChildren().add(Menu.restartButton.circle);
        BombermanGame.root.getChildren().add(Menu.homeButton.circle);
    }
}
