package gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import gamePlay.entity.Entity;

import java.util.List;


public abstract class MoveEntity extends Entity {

    protected int velocity;
    protected int realPositionX;
    protected int realPositionY;
    protected boolean endGame = false;


    public MoveEntity() {
        super();
        velocity = 2;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public int getRealPositionX() {
        return realPositionX;
    }

    public int getRealPositionY() {
        return realPositionY;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }

}
