package MenuGame;

public class SettingsLayer {
    public static void renderButton() {
        BombermanGame.root.getChildren().remove(MenuControl.helpButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.settingButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.highScoreButton.circle);
        BombermanGame.root.getChildren().remove(MenuControl.startButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.musicButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.soundButton.circle);
        BombermanGame.root.getChildren().add(MenuControl.backButton.circle);
    }
}
