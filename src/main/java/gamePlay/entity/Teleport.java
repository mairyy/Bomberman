package main.java.gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.gamePlay.Map;
import main.utils.ImageUtils;

public class Teleport extends Entity {
    private Map map;
    private double frame = 0;
    private double totalFrame = 5;
    private Image[] closeTeleport = new Image[5];
    private Image[] openTeleport = new Image[5];

    public Teleport(int positionX, int positionY, Map map) {
        this.map = map;
        loadImage("resource/image/Teleport.png");
        width = (int) image.getWidth()/5;
        height = (int) image.getHeight()/2;
        for(int i = 0; i < totalFrame; i++) {
            Image newImage = ImageUtils.crop(image, i*width, 0, width, height);
            closeTeleport[i] = newImage;
        }

        for(int i = 0; i < totalFrame; i++) {
            Image newImage = ImageUtils.crop(image, i*width, height, width, height);
            openTeleport[i] = newImage;
        }

        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void update() {
        //setFame;
        frame += 0.15;
        if(frame >= totalFrame) {
            frame = 0;
        }
        if (map.enemies.size() == 0 && !isDestroy()) {
            isDestroy = true;
            frame = 0;
        } else if(map.player.realPositionX == positionX && map.player.realPositionY == positionY
            && isDestroy){
            System.out.println("win");
        }
    }


    public void render(GraphicsContext gc) {
        if(!isDestroy) {
            gc.drawImage(closeTeleport[(int) frame], positionX, positionY);
        } else {
            gc.drawImage(openTeleport[(int) frame], positionX, positionY);
        }
    }
}
