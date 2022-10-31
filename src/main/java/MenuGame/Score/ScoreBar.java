package MenuGame.Score;

import MenuGame.MenuControl;
import MenuGame.BombermanGame;
import gamePlay.entity.Item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBar extends Score {
    public void load() {

    }

    public void render(GraphicsContext gc) {
        if (!BombermanGame.root.getChildren().contains(MenuControl.pauseButton.circle)) {
            BombermanGame.root.getChildren().add(MenuControl.pauseButton.circle);
        }
        String level = "";
        switch (BombermanGame.level) {
            case EASY:
                level = easy;
                break;
            case MEDIUM:
                level = medium;
                break;
            case HARD:
                level = hard;
                break;
        }

        StringBuffer s = new StringBuffer("LEVEL: "+ level + "  ");
        s.append(scoreStr).append(": ").append(BombermanGame.game.score).append("  ");
        s.append(timeStr).append(": ").append(String.format("%.0f", BombermanGame.game.maxTimeGame - BombermanGame.game.timeGame));
        drawText(gc, Color.WHITE, bodyFont, s.toString(), 10, 35);
        int itemPositionX = 560;
        for (Item i : BombermanGame.game.map.removeItems) {
            i.setPosition(itemPositionX, 10);
            i.setHeight(30);
            i.setWidth(30);
            i.render(gc);
            itemPositionX += 30;
        }
    }

    public void drawText(GraphicsContext gc, Color color, Font font, String str, int x, int y) {
        super.drawText(gc, color, font, str, x, y);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText(str, x, y);
    }
}
