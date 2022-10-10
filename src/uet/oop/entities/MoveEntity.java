package uet.oop.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;


public abstract class MoveEntity extends Entity {

    protected int velocity;
    protected boolean die;
    protected int realPositionX;
    protected int realPositionY;

    public MoveEntity() {
        super();
        velocity = 2;
        die = false;
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

    public boolean isDie() {
        return die;
    }

    public void setDie(boolean die) {
        this.die = die;
    }

}
