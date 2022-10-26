package MenuGame;

public class SettingsLayer {
    public static void renderButton() {
        BombermanGame.root.getChildren().remove(Menu.helpButton.circle);
        BombermanGame.root.getChildren().remove(Menu.settingButton.circle);
        BombermanGame.root.getChildren().remove(Menu.highScoreButton.circle);
        BombermanGame.root.getChildren().remove(Menu.startButton.circle);
        BombermanGame.root.getChildren().add(Menu.musicButton.circle);
        BombermanGame.root.getChildren().add(Menu.soundButton.circle);
        BombermanGame.root.getChildren().add(Menu.backButton.circle);
    }
}
