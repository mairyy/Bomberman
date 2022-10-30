package MenuGame.HighScore;

import MenuGame.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HighScoreLayer {
    public static int highScore;
    public static String ranking;
    public static Font font = Font.font("Comic Sans MS", FontWeight.BOLD, 30);
    public static Sprite highScoreImg = new Sprite(new Image("highScoreText.png"), 200, -50, 0, 0);

    public static String readFile(File path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String ranking = "";
        String s = bufferedReader.readLine();
        while (s != null) {
            ranking += s;
        }
        return ranking;
    }

    public static void load() {
        highScoreImg.setrHeight(400);
        highScoreImg.setrWidth(400);
        highScoreImg.load();
    }

    public static void render(GraphicsContext gc) throws IOException {
        highScoreImg.render(gc);
//        String str = "RANKING BOARD";
//        gc.setFill(Color.BLACK);
//        gc.setFont(font);
//        gc.fillText(str, 500, 200);
    }
}
