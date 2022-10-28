package MenuGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class PauseLayer {
    public static Sprite pauseImg = new Sprite(new Image("pauseLayer.png"), 0, 0, 0, 0);

    public static void load() {
        pauseImg.load();
    }

    public static void render(GraphicsContext gc) {
        pauseImg.render(gc);
        Menu.restartButton.circle.setCenterX(300);
        Menu.restartButton.circle.setCenterY(450);
        Menu.startButton.circle.setCenterX(400);
        Menu.startButton.circle.setCenterY(450);
        Menu.homeButton.circle.setCenterX(510);
        Menu.homeButton.circle.setCenterY(450);
        BombermanGame.root.getChildren().add(Menu.restartButton.circle);
        BombermanGame.root.getChildren().add(Menu.startButton.circle);
        BombermanGame.root.getChildren().add(Menu.homeButton.circle);
    }
}
