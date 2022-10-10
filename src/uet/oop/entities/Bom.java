package uet.oop.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.Main;
import main.utils.ImageUtils;

public class Bom extends Entity {
    int numberFrame = 4;
    int numberFameFire = 5;
    private Image[] animations = new Image[numberFrame];
    private Image[][] explodeImage = new Image[numberFameFire][7];
    public Bom(int positionX, int positionY) {
        loadImage("main/resource/image/bombs.png");
        width = (int) image.getWidth()/3;
        height = (int) image.getHeight();
        for (int i = 0; i < 3; i++) {
            Image newImage = ImageUtils.crop(image, i*width, 0, width, height);
            animations[i] = newImage;
        }
        animations[3] = animations[1];
        loadImage("main/resource/image/fires.png");
        width = (int) image.getWidth()/7;
        height = (int) image.getHeight()/numberFameFire;
        for (int i = 0; i < numberFameFire; i++) {
            for(int j = 0; j < 7; j++) {
                Image newImage = ImageUtils.crop(image, j*width, i*height, width, height);
                explodeImage[i][j] = newImage;
            }
        }
        this.positionX = positionX;
        this.positionY = positionY;
        width = Main.widthUnit;
        height = Main.widthUnit;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(animations[0], positionX, positionY, width, height);
    }

    
}
