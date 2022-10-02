package main.java;

public abstract class MoveEntity extends Entity {
    private int velocityX;
    private int velocityY;
    private boolean die;

    public MoveEntity() {
        super();
        velocityX = 0;
        velocityY = 0;
        die = false;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public boolean isDie() {
        return die;
    }

    public void setDie(boolean die) {
        this.die = die;
    }

    public void move(double time) {
        positionX += time * velocityX;
        positionY += time * velocityY;
    }
}
