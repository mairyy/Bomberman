package uet.oop.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.Main;
import main.utils.ImageUtils;

import java.util.List;

public class Player extends MoveEntity {
    private int numberStatus = 4; // left, right, up, down.
    private int numberFrame = 11;
    private int numberFrameDead = 7;
    private double frameDead = 0;
    private boolean endGame = false;
    double frame = 0;
    int status = 0;
    private Image[][] animations = new Image[numberStatus][numberFrame];
    private Image[] animationsDead = new Image[numberFrameDead];

    public Player() {
        loadImage("main/resource/image/BombermanSpriteSheet.png");
        width = (int) image.getWidth() / 7;
        height = (int) image.getHeight()/ 9;
        for (int i = 0; i < numberStatus; i++) {
            for (int j = 0; j < 4; j++) {
                Image newImage = ImageUtils.crop(image, j*width, i*height, width, height);
                animations[i][j] = newImage;
            }
        }
        for (int i = 0; i < numberStatus; i++) {
            for (int j = 0; j < 7; j++) {
                Image newImage = ImageUtils.crop(image, j*width, 4*height +i*height, width, height);
                animations[i][j+4] = newImage;
            }
        }
        for (int i = 0; i < numberFrameDead; i++) {
            Image newImage = ImageUtils.crop(image, i*width, 8*height, width, height);
            animationsDead[i] = newImage;
        }
        width = Main.widthUnit;
        height = Main.widthUnit;
        realPositionX = positionX;
        realPositionY = positionY;
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!isDie()) {
            gc.drawImage(animations[status][(int)frame], positionX, positionY, width, height);
        }
        else {
            gc.drawImage(animationsDead[(int)frameDead], positionX, positionY, width, height);
        }
    }

    public boolean isEndGame() {
        return endGame;
    }

    @Override
    public Rectangle2D getBoundary() {
        return new javafx.geometry.Rectangle2D(positionX, positionY, width-5, height-5);

    }

    public void setAnimations(List<KeyCode> events) {
        if(!events.isEmpty()) {
            if (events.contains(KeyCode.UP)) {
                status = 1;
                frame += 0.15;
            }
            else if (events.contains(KeyCode.DOWN)) {
                status = 0;
                frame += 0.15;
            }
            else if (events.contains((KeyCode.LEFT))) {
                status = 2;
                frame += 0.15;
            }
            else if (events.contains((KeyCode.RIGHT))) {
                status = 3;
                frame += 0.15;
            }
            if(frame >= numberFrame) {
                frame = 0;
            }
        }
        else {
            frame = 0;
        }
        if (isDie()) {
           frameDead += 0.1;
           if(frameDead >= numberFrameDead) {
               endGame = true;
           }
        }
    }

    public void handleMove(List<KeyCode> events, int[][] mapArr) {
        if(!isDie()) {
            if (events.contains(KeyCode.UP)) {
                if (positionY > (positionY/height)*height) {
                    positionY -= velocity;
                } else if(mapArr[realPositionY/height-1][realPositionX/width] == 1){
                    if (mapArr[positionY/height-1][positionX/width+1] == 1 &&
                            mapArr[positionY/height-1][positionX/width] == 1) {
                        positionY -= velocity;
                    } else {
                        positionX = realPositionX;
                        positionY -= velocity;
                    }
                }
            }
            if (events.contains(KeyCode.DOWN)) {
                if (positionY  != realPositionY) {
                    positionY += velocity;
                } else if(mapArr[realPositionY/height+1][realPositionX/width] == 1) {
                    if (mapArr[positionY/height+1][positionX/width+1] == 1 &&
                            mapArr[positionY/height+1][positionX/width] == 1) {
                        positionY += velocity;
                    }
                    else {
                        positionX = realPositionX;
                        positionY += velocity;
                    }
                }
            }
            if (events.contains(KeyCode.LEFT)) {
                if (positionX > (positionX/width)*width) {
                    positionX -= velocity;
                } else if (mapArr[realPositionY/height][realPositionX/width-1] == 1) {
                    if (mapArr[positionY/height+1][positionX/width-1] == 1 &&
                            mapArr[positionY/height][positionX/width-1] == 1) {
                        positionX -= velocity;
                    } else {
                        positionY = realPositionY;
                        positionX -= velocity;
                    }
                }
            }
            if (events.contains(KeyCode.RIGHT)) {
                if (positionX != realPositionX) {
                    positionX += velocity;
                } else if (mapArr[realPositionY/height][realPositionX/width+1] == 1) {
                    if (mapArr[positionY / height + 1][positionX / width + 1] == 1 &&
                            mapArr[positionY / height][positionX / width + 1] == 1) {
                        positionX += velocity;
                    }
                    else {
                        positionY = realPositionY;
                        positionX += velocity;
                    }
                }
            }
            realPositionX = ((positionX + width/2)/width) * width;
            realPositionY = ((positionY + height/2)/height) * height;
        }
    }
}
