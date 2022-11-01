package MenuGame;

import MenuGame.Button.Sprite;
import MenuGame.Score.Score;
import MenuGame.Score.Text;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameOver extends Score implements Text {
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
        drawText(gc, Color.BLACK, scoreFont, Integer.toString(BombermanGame.game.score), 355 ,500);
    }

    public void clear() {

    }
}
