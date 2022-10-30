package MenuGame.HighScore;

import MenuGame.MenuControl;
import MenuGame.BombermanGame;
import MenuGame.Button.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HighScoreLayer extends Text {
    public static Sprite highScoreImg = new Sprite(new Image("highScoreText.png"), 210, -90, 0, 0);

    public void load() {
        highScoreImg.setrHeight(400);
        highScoreImg.setrWidth(400);
        highScoreImg.load();
    }

    public void render(GraphicsContext gc) {
        highScoreImg.render(gc);
        BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
//        String str = "RANKING BOARD";
//        gc.setFill(Color.BLACK);
//        gc.setFont(font);
//        gc.fillText(str, 500, 200);
    }
}
