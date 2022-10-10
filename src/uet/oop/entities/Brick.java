package uet.oop.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.Main;
import main.utils.ImageUtils;

public class Brick extends Entity {
    boolean destroy = false;
    private int frame = 7;
    private Image[] images = new Image[frame];

    public Brick(int positionX, int positionY) {
        loadImage("main/resource/image/blocks3.png");
        width = (int) ((image.getWidth() - 7) / 8);
        height = (int) (image.getHeight());
        for (int i = 1; i <= 7; i++) {
            Image newImage = ImageUtils.crop(image, i * width, 0, width, height);
            images[i - 1] = newImage;
        }
        width = Main.widthUnit;
        height = Main.widthUnit;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroy) {
            gc.drawImage(images[0], positionX, positionY, width, height);
        }
    }
}