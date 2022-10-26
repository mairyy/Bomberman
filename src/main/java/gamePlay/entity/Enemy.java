package gamePlay.entity;

import gamePlay.entity.BFS.Point;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import BombermanGame;
import gamePlay.GamePlay;
import gamePlay.entity.BFS.BFS;
import gamePlay.utils.ImageUtils;
import gamePlay.Map;
import gamePlay.entity.MoveEntity;
import gamePlay.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


enum StatusMove {
    UP, DOWN, LEFT, RIGHT
}
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
    private Map map;

    public Enemy(int positionX, int positionY, Map map) {
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
        this.map = map;
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
   public void move() {
       if(!isDestroy()) {
           setAnimationMove();
           if (BombermanGame.level == BombermanGame.LEVEL.EASY) {
               randomMove(map.arrMap);
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

   public void setFrameDead() {
        frame += 0.1;
        if(frame >= numberOfFrameDEAD) {
            endGame = true;
        }
   }

    public void randomMove(int[][] mapArr) {
        Random generate = new Random();
        int num = generate.nextInt(4);
        if (statusMove == 0) {
            if (positionY != realPositionY) {
                positionY -= velocity;
            } else if (mapArr[realPositionY / height - 1][realPositionX / width] == 1) {
                positionY -= velocity;
            } else {
                statusMove = num;
            }
        }
        if (statusMove == 1) {
            if (positionY != realPositionY) {
                positionY += velocity;
            } else if (mapArr[realPositionY / height + 1][realPositionX / width] == 1) {
                positionY += velocity;
            } else {
                statusMove = num;
            }

        }
        if (statusMove == 2) {
            if (positionX != realPositionX) {
                positionX -= velocity;
            } else if (mapArr[realPositionY / height][realPositionX / width - 1] == 1) {
                positionX -= velocity;
            } else {
                statusMove = num;
            }
        }
        if (statusMove == 3) {
            if (positionX != realPositionX) {
                positionX += velocity;
            } else if (mapArr[realPositionY / height][realPositionX / width + 1] == 1) {
                positionX += velocity;
            } else {
                statusMove = num;
            }
        }
        realPositionX = ((positionX + width/2)/width) * width;
        realPositionY = ((positionY + height/2)/height) * height;
    }

    //xE, yE: enemy's position in map[][]
    //xP, yP: player's position in map[][]
    public void AIMove(int[][] mapArr, int xE, int yE, int xP, int yP) {
        BFS bfs = new BFS();
        List<Point> path = new ArrayList<>();
        BFS.findPath(mapArr, bfs.visited, xE, yE, xP, yP);
        StatusMove statusMove;
        if (path.get(0).x * width != positionX) {
            if (path.get(0).x * width > positionX) {
                statusMove = StatusMove.RIGHT;
            } else {
                statusMove = StatusMove.LEFT;
            }
        } else {
            if (path.get(0).y * height > positionY) {
                statusMove = StatusMove.DOWN;
            } else {
                statusMove = StatusMove.UP;
            }
        }
        handleMove(statusMove, mapArr);
    }


    public void handleMove(StatusMove statusMove, int[][] mapArr) {
        if (!isDestroy()) {
            if (statusMove == StatusMove.UP) {
                if (positionY - velocity > (positionY / height) * height) {
                    positionY -= velocity;
                } else if (mapArr[realPositionY / height - 1][realPositionX / width] == 1) {
                    if (mapArr[positionY / height - 1][positionX / width + 1] == 1 &&
                            mapArr[positionY / height - 1][positionX / width] == 1) {
                        positionY -= velocity;
                    } else {
                        positionX = realPositionX;
                        positionY -= velocity;
                    }
                } else if(positionY > (positionY / height) * height){
                    positionY = (positionY / height) * height;
                }
            }
            if (statusMove == StatusMove.DOWN) {
                if (positionY + velocity < realPositionY) {
                    positionY += velocity;
                } else if (mapArr[realPositionY / height + 1][realPositionX / width] == 1) {
                    if (mapArr[positionY / height + 1][positionX / width + 1] == 1 &&
                            mapArr[positionY / height + 1][positionX / width] == 1) {
                        positionY += velocity;
                    } else {
                        positionX = realPositionX;
                        positionY += velocity;
                    }
                } else if (positionY < realPositionY) {
                    positionY = realPositionY;
                }
            }
            if (statusMove == StatusMove.LEFT) {
                if (positionX - velocity > (positionX / width) * width) {
                    positionX -= velocity;
                } else if (mapArr[realPositionY / height][realPositionX / width - 1] == 1) {
                    if (mapArr[positionY / height + 1][positionX / width - 1] == 1 &&
                            mapArr[positionY / height][positionX / width - 1] == 1) {
                        positionX -= velocity;
                    } else {
                        positionY = realPositionY;
                        positionX -= velocity;
                    }
                } else if (positionX > (positionX / width) * width) {
                    positionX = (positionX / width) * width;
                }
            }
            if (statusMove == StatusMove.RIGHT) {
                if (positionX + velocity < realPositionX) {
                    positionX += velocity;
                } else if (positionX < realPositionX) {
                    positionX = realPositionX;
                } else if (mapArr[realPositionY / height][realPositionX / width + 1] == 1) {
                    if (mapArr[positionY / height + 1][positionX / width + 1] == 1 &&
                            mapArr[positionY / height][positionX / width + 1] == 1) {
                        positionX += velocity;
                    } else {
                        positionY = realPositionY;
                        positionX += velocity;
                    }
                }
            }
            realPositionX = ((positionX + width / 2) / width) * width;
            realPositionY = ((positionY + height / 2) / height) * height;
        }
    }
}
