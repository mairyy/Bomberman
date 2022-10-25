package main.java.gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.java.gamePlay.GamePlay;
import main.java.gamePlay.Map;
import main.utils.ImageUtils;

import java.util.List;

public class Brick extends Entity {
    private int numberFrame = 7;
    private boolean isEnd = false;
    private double frame = 1;
    private Image[] images = new Image[numberFrame];
    private Map map;

    public Brick(int positionX, int positionY, Map map) {
        loadImage("resource/image/blocks3.png");
        width = (int) ((image.getWidth() - 7) / 8);
        height = (int) (image.getHeight());
        for (int i = 1; i <= 7; i++) {
            Image newImage = ImageUtils.crop(image, i * width, 0, width, height);
            images[i - 1] = newImage;
        }
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        this.positionX = positionX;
        this.positionY = positionY;
        this.map = map;
    }


    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDestroy) {
            gc.drawImage(images[0], positionX, positionY, width, height);
        } else {
            if(!isEnd) {
                gc.drawImage(images[(int) frame], positionX, positionY, width, height);
            }
        }
    }


    public void update(List<Integer> toRemove) {
        if(isDestroy) {
            if(!isEnd) {
                frame += 0.15;
                if (frame >= numberFrame) {
                    isEnd = true;
                }
            }
            else {
                map.arrMap[positionY/height][positionX/width] = 1;
                toRemove.add(positionX/width* map.getLenWidth()*10 + positionY/height);
            }
        }
    }
}