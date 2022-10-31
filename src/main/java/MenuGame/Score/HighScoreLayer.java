package MenuGame.Score;

import MenuGame.MenuControl;
import MenuGame.BombermanGame;
import MenuGame.Button.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;

public class HighScoreLayer extends Score {
    public static Sprite highScoreImg = new Sprite(new Image("highScoreLayer.png"), 0, 0, 0, 0);
    static String[] ranking = new String[5];

    public void load() {
        highScoreImg.load();
        ranking = readFile(new File("res/resource/map/record.txt")).split(" ");
    }

    public void render(GraphicsContext gc) {
        highScoreImg.render(gc);
        BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
        MenuControl.restartButton.circle.setCenterX(170);
        MenuControl.restartButton.circle.setCenterY(740);
        BombermanGame.root.getChildren().add(MenuControl.restartButton.circle);

        drawText(gc, Color.BLACK, titleFont, levelStr, 170, 280);
        drawText(gc, Color.BLACK, titleFont, scoreStr, 400, 280);
        drawText(gc, Color.BLACK, titleFont, timeStr, 580, 280);

        drawText(gc, Color.BLACK, bodyFont, easy, 170, 380);
        drawText(gc, Color.BLACK, bodyFont, medium, 170, 480);
        drawText(gc, Color.BLACK, bodyFont, hard, 170, 580);

        int x = 400, y = 380;
        for (int i = 0; i < ranking.length; i++) {
            if (i % 2 == 0) {
                drawText(gc, Color.BLACK, bodyFont, ranking[i], x, y);
                x += 180;
            } else {
                drawText(gc, Color.BLACK, bodyFont, String.format("%.2f", Float.parseFloat(ranking[i])), x, y);
                y += 100;
                x -= 180;
            }
        }
    }

    public void clear() {
        BombermanGame.root.getChildren().remove(MenuControl.restartButton.circle);
    }
}
