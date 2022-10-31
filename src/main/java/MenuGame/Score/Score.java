package MenuGame.Score;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class Score {
    protected Font titleFont = Font.font("Comic Sans MS", FontWeight.BOLD, 30);
    protected Font bodyFont = Font.font("Comic Sans MS", FontWeight.BOLD, 25);
    protected final String scoreStr = "SCORE";
    protected final String timeStr = "TIME";
    protected final String levelStr = "LEVEL";
    protected final String easy = "EASY";
    protected final String medium = "MEDIUM";
    protected final String hard = "HARD";

    protected String readFile(File path) {
        StringBuffer ranking = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String s = bufferedReader.readLine();
            while (s != null) {
                ranking.append(s);
                s = bufferedReader.readLine();
            }
            return ranking.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ranking.toString();
    }

    protected void drawText(GraphicsContext gc, Color color, Font font, String str, int x, int y) {
        gc.setFill(color);
        gc.setFont(font);
        gc.fillText(str, x, y);
    }

    protected abstract void load();

    protected abstract void render(GraphicsContext gc);
}
