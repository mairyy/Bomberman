package gamePlay.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import gamePlay.GamePlay;
import gamePlay.utils.ImageUtils;
import gamePlay.entity.MoveEntity;
import gamePlay.entity.Entity;

import java.util.List;

public class Player extends MoveEntity {
    private final int numberStatus = 4; // left, right, up, down.
    private final int numberFrame = 11;
    private final int numberFrameDead = 7;
    private int maxTotalBom = 1;
    private double frameDead = 0;
    double frame = 0;
    int status = 0;
    private final Image[][] animations = new Image[numberStatus][numberFrame];
    private final Image[] animationsDead = new Image[numberFrameDead];

    public int getMaxTotalBom() {
        return maxTotalBom;
    }

    public void setMaxTotalBom(int maxTotalBom) {
        this.maxTotalBom = maxTotalBom;
    }

    public Player() {
        loadImage("resource/image/BombermanSpriteSheet.png");
        width = (int) image.getWidth() / 7;
        height = (int) image.getHeight() / 9;
        for (int i = 0; i < numberStatus; i++) {
            for (int j = 0; j < 4; j++) {
                Image newImage = ImageUtils.crop(image, j * width, i * height, width, height);
                animations[i][j] = newImage;
            }
        }
        for (int i = 0; i < numberStatus; i++) {
            for (int j = 0; j < 7; j++) {
                Image newImage = ImageUtils.crop(image, j * width, 4 * height + i * height, width, height);
                animations[i][j + 4] = newImage;
            }
        }
        for (int i = 0; i < numberFrameDead; i++) {
            Image newImage = ImageUtils.crop(image, i * width, 8 * height, width, height);
            animationsDead[i] = newImage;
        }
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        realPositionX = positionX;
        realPositionY = positionY;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDestroy()) {
            gc.drawImage(animations[status][(int) frame], positionX, positionY, width, height);
        } else {
            gc.drawImage(animationsDead[(int) frameDead], positionX, positionY, width, height);
        }
    }

    public boolean isEndGame() {
        return endGame;
    }

    @Override
    public Rectangle2D getBoundary() {
        return new javafx.geometry.Rectangle2D(positionX + width / 4, positionY + height / 4, width - 25, height - 25);

    }

    public void setAnimations(List<KeyCode> events, double time) {
        if (!events.isEmpty()) {
            if (events.contains(KeyCode.UP)) {
                status = 1;
                frame += time * 10;
            } else if (events.contains(KeyCode.DOWN)) {
                status = 0;
                frame += time * 10;
            } else if (events.contains((KeyCode.LEFT))) {
                status = 2;
                frame += time * 10;
            } else if (events.contains((KeyCode.RIGHT))) {
                status = 3;
                frame += time * 10;
            }
            if (frame >= numberFrame) {
                frame = 0;
            }
        } else {
            frame = 0;
        }
        if (isDestroy()) {
            frameDead += 0.1;
            if (frameDead >= numberFrameDead) {
                endGame = true;
            }
        }
    }

    //move
    public void handleMove(List<KeyCode> events, int[][] mapArr) {
        if (!isDestroy()) {
            if (events.contains(KeyCode.UP)) {
                moveUp(mapArr);
            }
            if (events.contains(KeyCode.DOWN)) {
                moveDown(mapArr);
            }
            if (events.contains(KeyCode.LEFT)) {
                moveLeft(mapArr);
            }
            if (events.contains(KeyCode.RIGHT)) {
                moveRight(mapArr);
            }
            realPositionX = ((positionX + width / 2) / width) * width;
            realPositionY = ((positionY + height / 2) / height) * height;
        }
    }
}
