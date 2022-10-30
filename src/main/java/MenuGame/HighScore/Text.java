package MenuGame.HighScore;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Text {
    protected Font font = Font.font("Comic Sans MS", FontWeight.BOLD, 30);
    protected int score = 0;

    protected String readFile(File path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String ranking = "";
        String s = bufferedReader.readLine();
        while (s != null) {
            ranking += s;
        }
        return ranking;
    }

    protected abstract void load();

    protected abstract void render(GraphicsContext gc);
}
