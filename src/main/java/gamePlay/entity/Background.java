package main.java.gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import main.java.gamePlay.GamePlay;

public class Background extends Entity {
    public Background() {
        loadImage("resource/image/grass.png");
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
    }

    public void render(GraphicsContext gc, int widthScreen, int heightScreen) {
        gc.drawImage(image, 0,0, widthScreen, heightScreen);
    }
}
