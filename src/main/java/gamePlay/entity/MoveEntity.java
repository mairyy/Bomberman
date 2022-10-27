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
    private boolean canMoveBrick = false;


    public MoveEntity() {
        super();
        velocity = 2;
    }

    public void setCanMoveBrick(boolean canMoveBrick) {
        this.canMoveBrick = canMoveBrick;
    }

    public boolean isCanMoveBrick() {
        return canMoveBrick;
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

    public void moveUp(int[][] mapArr) {
        if (proviso(mapArr[realPositionY / height - 1][realPositionX / width])) {
            if (positionX < realPositionX) {
                //moveRight(mapArr);
                if (positionX + velocity < realPositionX) {
                    positionX += velocity;
                } else {
                    positionX = realPositionX;
                }
            } else if (positionX > realPositionX) {
                //moveLeft(mapArr);
                if (positionX - velocity > realPositionX) {
                    positionX -= velocity;
                } else {
                    positionX = realPositionX;
                }
            } else {
                positionY -= velocity;
            }
        } else if (positionY - velocity > realPositionY) {
            positionY -= velocity;
        } else {
            positionY = realPositionY;
        }
    }

    public void moveDown(int[][] mapArr) {
        if (proviso(mapArr[realPositionY / height + 1][realPositionX / width])) {
            if (positionX < realPositionX) {
                //moveRight(mapArr);
                if (positionX + velocity < realPositionX) {
                    positionX += velocity;
                } else {
                    positionX = realPositionX;
                }
            } else if (positionX > realPositionX) {
                //moveLeft(mapArr);
                if (positionX - velocity > realPositionX) {
                    positionX -= velocity;
                } else {
                    positionX = realPositionX;
                }
            } else {
                positionY += velocity;
            }
        } else if (positionY + velocity < realPositionY) {
            positionY += velocity;
        } else {
            positionY = realPositionY;
        }
    }

    public void moveLeft(int[][] mapArr) {
        if (proviso(mapArr[realPositionY / height][realPositionX / width - 1])) {
            if (positionY > realPositionY) {
                //moveUp(mapArr);
                if (positionY - velocity > realPositionY) {
                    positionY -= velocity;
                } else {
                    positionY = realPositionY;
                }
            } else if (positionY < realPositionY) {
                //moveDown(mapArr);
                if (positionY + velocity < realPositionY) {
                    positionY += velocity;
                } else {
                    positionY = realPositionY;
                }
            } else {
                positionX -= velocity;
            }
        } else if (positionX - velocity > realPositionX) {
            positionX -= velocity;
        } else {
            positionX = realPositionX;
        }
    }

    public void moveRight(int[][] mapArr) {
        if (proviso(mapArr[realPositionY / height][realPositionX / width + 1])) {
            if (positionY > realPositionY) {
                //moveUp(mapArr);
                if (positionY - velocity > realPositionY) {
                    positionY -= velocity;
                } else {
                    positionY = realPositionY;
                }
            } else if (positionY < realPositionY) {
                //moveDown(mapArr);
                if (positionY + velocity < realPositionY) {
                    positionY += velocity;
                } else {
                    positionY = realPositionY;
                }
            } else {
                positionX += velocity;
            }
        } else if (positionX + velocity < realPositionX) {
            positionX += velocity;
        } else {
            positionX = realPositionX;
        }
    }

    public boolean proviso(int number) {
        if (isCanMoveBrick()) {
            if (number != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (number == 1) {
                return true;
            } else {
                return false;
            }
        }
    }
}
