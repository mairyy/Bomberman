package MenuGame.Score;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public interface Text {
    Font scoreFont = Font.font("Courier New", FontWeight.BOLD, 50);
    Font titleFont = Font.font("Comic Sans MS", FontWeight.BOLD, 30);
    Font bodyFont = Font.font("Comic Sans MS", FontWeight.BOLD, 25);
    String scoreStr = "SCORE";
    String timeStr = "TIME";
    String levelStr = "LEVEL";
    String easy = "EASY";
    String medium = "MEDIUM";
    String hard = "HARD";
}
