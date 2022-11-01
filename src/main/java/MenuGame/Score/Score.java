package MenuGame.Score;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;

public abstract class Score implements Text {
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

    protected void writeFile(File path, String s) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(s.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void drawText(GraphicsContext gc, Color color, Font font, String str, int x, int y) {
        gc.setFill(color);
        gc.setFont(font);
        gc.fillText(str, x, y);
    }

    protected abstract void load();

    protected abstract void render(GraphicsContext gc);
}
