package gamePlay.entity.Item;

import gamePlay.GamePlay;
import gamePlay.Map;
import gamePlay.utils.ImageUtils;
import javafx.scene.image.Image;

public class AntiBomb extends Item{
    public AntiBomb(int positionX, int positionY, Map map) {
        loadImage("resource/image/PowerUps.png");
        width = (int) (image.getWidth()-27)/10;
        height = (int) (image.getHeight()-6)/3;
        Image newImage = ImageUtils.crop(image, (3+width)*6, height+3, width, height);
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
            map.player.setAntiBomb(true);
            isDestroy = true;
        }
    }
}
