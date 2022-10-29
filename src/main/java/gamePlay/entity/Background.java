package gamePlay.entity;

import gamePlay.utils.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import gamePlay.GamePlay;
import javafx.scene.image.Image;

public class Background extends Entity {
    private Image brick;
    public Background() {
        loadImage("resource/image/grass.png");
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        brick = new Image("resource/image/blocks1.png");
        width = (int) (brick.getWidth() - 21)/8;
        height = (int) brick.getHeight();
        brick = ImageUtils.crop(brick, 0, 0, width, height);
        width = 50;
        height = 50;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, 0,0, 800, 800);
        for(int i = 0; i < 16; i++) {
            gc.drawImage(brick, width*i, 0, width, height);
        }
    }
}
