package MenuGame;

import javafx.scene.canvas.GraphicsContext;

public class SettingsLayer extends Menu {
    public void load() {

    }
    public void render(GraphicsContext gc) {
        BombermanGame.root.getChildren().remove(MenuControl.helpButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.settingButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.highScoreButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.startButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.musicButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.soundButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
    }

    public void clear() {
        BombermanGame.root.getChildren().remove(MenuControl.musicButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.soundButton.circle);
    }
}
