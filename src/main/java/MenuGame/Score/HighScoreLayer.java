package MenuGame.Score;

import MenuGame.MenuControl;
import MenuGame.BombermanGame;
import MenuGame.Button.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;

public class HighScoreLayer extends Score implements Text {
    public boolean isHighScoreLayer = false;
    public static Sprite highScoreImg = new Sprite(new Image("highScoreLayer.png"), 0, 0, 0, 0);
    static String[] ranking = new String[6];
    public static double[] timeRecord = new double[3];
    public static int[] highScore = new int[3];

    public void load() {
        highScoreImg.load();
        ranking = readFile(new File("res/resource/map/record.txt")).split(" ");
        int indexTime = 0, indexhighScore = 0;
        for (int i = 0; i < ranking.length; i++) {
            if (i % 2 == 0) {
                highScore[indexhighScore] = Integer.parseInt(ranking[i]);
                System.out.println(highScore[indexhighScore]);
                indexhighScore++;
            } else {
                timeRecord[indexTime] = Double.parseDouble(ranking[i]);
                System.out.println(timeRecord[indexTime]);
                indexTime++;
            }
        }
    }

    public void render(GraphicsContext gc) {
        isHighScoreLayer = true;
        highScoreImg.render(gc);
        if (!BombermanGame.root.getChildren().contains(MenuControl.backButton.circle)) {
            BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
            MenuControl.restartButton.circle.setCenterX(170);
            MenuControl.restartButton.circle.setCenterY(740);
            BombermanGame.root.getChildren().add(MenuControl.restartButton.circle);
        }

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
                drawText(gc, Color.BLACK, bodyFont, String.format("%.0f", Double.parseDouble(ranking[i])), x, y);
                y += 100;
                x -= 180;
            }
        }
    }

    public void writeRecord() {
        switch (BombermanGame.level) {
            case EASY:
                if (highScore[0] < BombermanGame.game.score ||
                        (highScore[0] == BombermanGame.game.score && timeRecord[0] > BombermanGame.game.timeGame)) {
                    highScore[0] = BombermanGame.game.score;
                    timeRecord[0] = BombermanGame.game.timeGame;
                }
                break;
            case MEDIUM:
                if (highScore[1] < BombermanGame.game.score ||
                        (highScore[1] == BombermanGame.game.score && timeRecord[1] > BombermanGame.game.timeGame)) {
                    highScore[1] = BombermanGame.game.score;
                    timeRecord[1] = BombermanGame.game.timeGame;
                }
                break;
            case HARD:
                if (highScore[2] < BombermanGame.game.score ||
                        (highScore[2] == BombermanGame.game.score && timeRecord[2] > BombermanGame.game.timeGame)) {
                    highScore[2] = BombermanGame.game.score;
                    timeRecord[2] = BombermanGame.game.timeGame;
                }
                break;
        }
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            s.append(highScore[i]).append(" ").append(timeRecord[i]).append(" ");
        }
        writeFile(new File("res/resource/map/record.txt"), s.toString());
    }

    public void resetRecord() {
        String s = "0 0 0 0 0 0";
        writeFile(new File("res/resource/map/record.txt"), s);
    }

    public void clear() {
        BombermanGame.root.getChildren().remove(MenuControl.restartButton.circle);
    }
}
