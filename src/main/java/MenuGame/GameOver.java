package MenuGame;

import MenuGame.Button.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameOver extends Menu {
    public static Sprite gameOverImg = new Sprite(new Image("loseLayer.png"), 0, 0, 0, 0);

    public void load() {
        gameOverImg.load();
    }

    public void render(GraphicsContext gc) {
        gameOverImg.render(gc);
        MenuControl.restartButton.circle.setCenterX(350);
        MenuControl.restartButton.circle.setCenterY(650);
        MenuControl.homeButton.circle.setCenterX(450);
        MenuControl.homeButton.circle.setCenterY(650);
        BombermanGame.root.getChildren().add(MenuControl.restartButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.homeButton.circle);
    }

    public void clear() {

    }
}
