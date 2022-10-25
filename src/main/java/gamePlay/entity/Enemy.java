package main.java.gamePlay.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import main.java.gamePlay.GamePlay;
import main.utils.ImageUtils;

import java.util.List;

public class Enemy extends MoveEntity {
    private int numberOfFrameAlive = 4;
    private int numberOfFrameDEAD = 5;
    private double frame = 0;
    private int status = 0;
    private int statusMove = 0;
    //0 là lên, 1 là xuống, 2 là sang trái, 3 là sang phải.
    private int directionNumber;
    private Image[][] animationMove = new Image[2][4];
    private Image[] animationDead = new Image[5];

    public void setFrame(double frame) {
        this.frame = frame;
    }

    public Enemy(int positionX, int positionY) {
        loadImage("resource/image/enemies.png");
        width = (int) (image.getWidth()/13);
        height = (int) 54;
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < numberOfFrameAlive; j++) {
                Image newImage = ImageUtils.crop(image, (i*4 + j)*width, height*0, width, height);
                animationMove[i][j] = newImage;
            }
        }
        for (int i = 0; i < numberOfFrameDEAD; i++) {
            Image newImage = ImageUtils.crop(image, (i+numberOfFrameAlive*2)*width, 0, width, height);
            animationDead[i] = newImage;
        }
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        this.positionX = positionX;
        this.positionY = positionY;
        realPositionX = positionX;
        realPositionY = positionY;
        directionNumber = (int) (Math.random() * 2);
        velocity = 1;
    }


    @Override
    public void render(GraphicsContext gc) {
        if(!isDestroy) {
            gc.drawImage(animationMove[status][(int)frame], getPositionX(), getPositionY(), width, height);
        } else {
            setFrameDead();
            if(!endGame){
                gc.drawImage(animationDead[(int)frame], positionX, positionY, width, height);
            }
        }
    }
   public void move(int[][] mapArr) {
       if(!isDestroy()) {
           setAnimationMove();
           if (statusMove == 0) {
               if (positionY > (positionY/height)*height) {
                   positionY -= velocity;
               } else if(mapArr[realPositionY/height-1][realPositionX/width] == 1){
                   positionY -= velocity;
               } else {
                   statusMove = 1;
               }
           }
           if (statusMove == 1) {
               if (positionY  != realPositionY) {
                   positionY += velocity;
               } else if(mapArr[realPositionY/height+1][realPositionX/width] == 1) {
                   positionY += velocity;
               } else {
                   statusMove = 0;
               }

           }
           if (statusMove == 2) {
               if (positionX > (positionX/width)*width) {
                   positionX -= velocity;
               } else if (mapArr[realPositionY/height][realPositionX/width-1] == 1) {
                   positionX += velocity;
               } else {
                   statusMove = 3;
               }
           }
           if (statusMove == 3) {
               if (positionX != realPositionX) {
                   positionX += velocity;
               } else if (mapArr[realPositionY/height][realPositionX/width+1] == 1) {
                   positionX -= velocity;
               } else {
                   statusMove = 2;
               }
           }
           realPositionX = ((positionX + width/2)/width) * width;
           realPositionY = ((positionY + height/2)/height) * height;
       }
   }

   public void setAnimationMove() {
        if(velocity > 0) {
            status = 1;
        } else {
            status = 0;
        }
        frame += 0.1;
        if(frame >= numberOfFrameAlive) {
            frame = 0;
        }
   }

   public void setFrameDead() {
        frame += 0.1;
        if(frame >= numberOfFrameDEAD) {
            endGame = true;
        }
   }


}
