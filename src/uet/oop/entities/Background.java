package uet.oop.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.Main;

public class Background extends Entity {
    public Background() {
        loadImage("main/resource/image/grass.png");
        width = Main.widthUnit;
        height = Main.widthUnit;
    }

    public void render(GraphicsContext gc, int widthScreen, int heightScreen) {
        gc.drawImage(image, 0,0, widthScreen, heightScreen);
    }
}
