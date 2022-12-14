package gamePlay.entity.Item;

import javafx.scene.image.Image;
import gamePlay.GamePlay;
import gamePlay.Map;
import gamePlay.entity.Bom;
import gamePlay.utils.ImageUtils;

import java.awt.*;

public class PowerUpBom extends Item {
    public PowerUpBom(int positionX, int positionY, Map map) {
        loadImage("resource/image/PowerUps.png");
        width = (int) (image.getWidth()-27)/10;
        height = (int) (image.getHeight()-6)/3;
        Image newImage = ImageUtils.crop(image, (3+width)*2, 0, width, height);
        this.image = newImage;
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        this.positionX = positionX;
        this.positionY = positionY;
        this.map = map;
    }

    @Override
    public void update() {
        if(checkColling()) {
            map.setPowerBom(map.getPowerBom() + 1);
            isDestroy = true;
        }
    }
}
