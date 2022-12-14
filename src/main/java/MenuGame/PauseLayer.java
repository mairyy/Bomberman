package MenuGame;

import MenuGame.Button.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class PauseLayer extends Menu {
    public static Sprite pauseImg = new Sprite(new Image("pauseLayer.png"), 0, 0, 0, 0);

    public void load() {
        pauseImg.load();
    }

    public void render(GraphicsContext gc) {
        pauseImg.render(gc);
        MenuControl.restartButton.circle.setCenterX(300);
        MenuControl.restartButton.circle.setCenterY(450);
        MenuControl.startButton.circle.setCenterX(400);
        MenuControl.startButton.circle.setCenterY(450);
        MenuControl.homeButton.circle.setCenterX(510);
        MenuControl.homeButton.circle.setCenterY(450);
        BombermanGame.root.getChildren().add(MenuControl.restartButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.startButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.homeButton.circle);
    }

    public void clear() {
        BombermanGame.root.getChildren().remove(MenuControl.restartButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.startButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.homeButton.circle);
    }
}
