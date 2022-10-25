package main.java.gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import main.utils.ImageUtils;
import javafx.scene.image.Image;
import main.java.gamePlay.GamePlay;

public class Wall extends Entity{
    public Wall(int positionX, int positionY) {
        Image newImage = new Image("resource/image/blocks3.png");
        width = (int) ((newImage.getWidth() - 7) / 8);
        height = (int) (newImage.getHeight());
        image = ImageUtils.crop(newImage, 0, 0, width, height);
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        this.positionX = positionX;
        this.positionY = positionY;
    }

}
