package uet.oop.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.Main;
import main.utils.ImageUtils;

import java.util.List;

public class Enemy extends MoveEntity {
    private int numberOfFrameAlive = 4;
    private int numberOfFrameDEAD = 5;
    private double frame = 0;
    private int status = 0;
    private int directionNumber;
    private Image[][] animationMove = new Image[2][4];
    private Image[] animationDead = new Image[5];

    public Enemy(int positionX, int positionY) {
        loadImage("main/resource/image/enemies.png");
        width = (int) image.getWidth()/13;
        height = (int) image.getHeight()/9;
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < numberOfFrameAlive; j++) {
                Image newImage = ImageUtils.crop(image, (i*4 + j)*width, 0, width, height);
                animationMove[i][j] = newImage;
            }
        }
        for (int i = 0; i < numberOfFrameDEAD; i++) {
            Image newImage = ImageUtils.crop(image, (i+numberOfFrameAlive*2), 0, width, height);
            animationDead[i] = newImage;
        }
        width = Main.widthUnit;
        height = Main.widthUnit;
        this.positionX = positionX;
        this.positionY = positionY;
        directionNumber = (int) (Math.random() * 2);
        velocity = 1;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(animationMove[status][(int)frame], getPositionX(), getPositionY(), width, height);
    }
   public void move(List<Entity> walls) {
        setAnimationMove();
       this.setPositionX(this.getPositionX() + this.getVelocity());
       for (Entity wall : walls) {
           if(wall.isColling(this)) {
               this.setPositionX(this.getPositionX() - this.getVelocity());
               this.setVelocity(-this.getVelocity());
           }
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


}
