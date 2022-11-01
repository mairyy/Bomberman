package gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import gamePlay.Map;
import gamePlay.utils.ImageUtils;
import gamePlay.GamePlay;
import MenuGame.WinLayer;
import gamePlay.entity.Entity;

public class Teleport extends Entity {
    private Map map;
    private double frame = 0;
    private double totalFrame = 5;
    private Image[] closeTeleport = new Image[5];
    private Image[] openTeleport = new Image[5];

    public Teleport(int positionX, int positionY, Map map) {
        this.map = map;
        loadImage("resource/image/Teleport.png");
        width = (int) image.getWidth() / 5;
        height = (int) image.getHeight() / 2;
        for (int i = 0; i < totalFrame; i++) {
            Image newImage = ImageUtils.crop(image, i * width, 0, width, height);
            closeTeleport[i] = newImage;
        }

        for (int i = 0; i < totalFrame; i++) {
            Image newImage = ImageUtils.crop(image, i * width, height, width, height);
            openTeleport[i] = newImage;
        }

        this.positionX = positionX;
        this.positionY = positionY;
    }

    // xử lý cổng đóng mở.
    public void update(double time) {
        //setFame;
        frame += time * 10;
        if (frame >= totalFrame) {
            frame = 0;
        }
        if (map.enemies.size() == 0 && !isDestroy()) {
            isDestroy = true;
            frame = 0;
        } else if (map.player.realPositionX == positionX && map.player.realPositionY == positionY
                && isDestroy && map.arrMap[positionY / width][positionX / height] != 2) {
            //System.out.println("win");
            map.gamePlay.isEnd = true;
        }
    }


    public void render(GraphicsContext gc) {
        if (!isDestroy) {
            gc.drawImage(closeTeleport[(int) frame], positionX, positionY);
        } else {
            gc.drawImage(openTeleport[(int) frame], positionX, positionY);
        }
    }
}
