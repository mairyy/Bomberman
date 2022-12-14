package gamePlay.entity;

import gamePlay.entity.BFS.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import MenuGame.BombermanGame;
import gamePlay.GamePlay;
import gamePlay.entity.BFS.BFS;
import gamePlay.utils.ImageUtils;
import gamePlay.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


enum StatusMove {
    UP, DOWN, LEFT, RIGHT
}

public class Enemy extends MoveEntity {
    private int numberOfType;
    //0 normal
    //1 AI
    //2 move brick
    //8 eat bom
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

    @Override
    public void setDestroy(boolean destroy) {
        super.setDestroy(destroy);
        frame = 0;
    }

    public Enemy(int positionX, int positionY, Map map, int numberOfType) {
        loadImage("resource/image/enemies.png");
        width = (int) (image.getWidth() / 13);
        height = (int) 54;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numberOfFrameAlive; j++) {
                Image newImage = ImageUtils.crop(image, (i * 4 + j) * width, height * numberOfType, width, height);
                animationMove[i][j] = newImage;
            }
        }
        for (int i = 0; i < numberOfFrameDEAD; i++) {
            Image newImage = ImageUtils.crop(image, (i + numberOfFrameAlive * 2) * width, height * numberOfType, width, height);
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
        this.numberOfType = numberOfType;
        if (numberOfType == 2) {
            setCanMoveBrick(true);
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        if (!isDestroy) {
            gc.drawImage(animationMove[status][(int) frame], getPositionX(), getPositionY(), width, height);
        } else {
            if (!endGame) {
                gc.drawImage(animationDead[(int) frame], positionX, positionY, width, height);
            }
        }
    }

    //xử lý di chuyển.
    public void move() {
        if (!isDestroy()) {
            if (numberOfType == 0) {
                randomMove(map.arrMap);
            }
            if (numberOfType == 1) {
                AIMove(map.arrMap, realPositionY / width, realPositionX / height, map.player.realPositionY / width, map.player.realPositionX / height);
            }
            if (numberOfType == 2) {
                randomMove(map.arrMap);
            }
            if (numberOfType == 8) {
                randomMove(map.arrMap);
                eatBom();
            }
        }
    }

    //xử lý ăn bom với enemy có thể ăn bom.
    public void eatBom() {
        for (int i = 0; i < map.boms.size(); i++) {
            if (this.isColling(map.boms.get(i))) {
                map.arrMap[map.boms.get(i).positionY / width][map.boms.get(i).positionX / height] = 1;
                map.boms.remove(i);
            }
        }
    }

    //xử lý animation di chuyển.
    public void setAnimationMove(double time) {
        if (velocity > 0) {
            status = 1;
        } else {
            status = 0;
        }
        frame += time * 10;
        if (frame >= numberOfFrameAlive) {
            frame = 0;
        }
    }

    //xử lý animation chết.
    public void setFrameDead(double time) {
        frame += time * 10;
        if (frame >= numberOfFrameDEAD) {
            endGame = true;
        }
    }

    //xử lý với enemy có thể xuyên tường.
    public boolean provisoEnemy(int number) {
        if (isCanMoveBrick()) {
            if (number != 0) {
                return true;
            } else {
                return false;
            }
        } else if (numberOfType != 8) {
            if (number == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            if (number == 1 || number == 6) {
                return true;
            }
            return false;
        }
    }

    //thuật toán random move.
    public void randomMove(int[][] mapArr) {
        Random generate = new Random();
        int num = generate.nextInt(4);
        if (statusMove == 0) {
            if (positionY != realPositionY) {
                positionY -= velocity;
            } else if (provisoEnemy(mapArr[realPositionY / height - 1][realPositionX / width])) {
                positionY -= velocity;
            } else {
                statusMove = num;
            }
        }
        if (statusMove == 1) {
            if (positionY != realPositionY) {
                positionY += velocity;
            } else if (provisoEnemy(mapArr[realPositionY / height + 1][realPositionX / width])) {
                positionY += velocity;
            } else {
                statusMove = num;
            }

        }
        if (statusMove == 2) {
            if (positionX != realPositionX) {
                positionX -= velocity;
            } else if (provisoEnemy(mapArr[realPositionY / height][realPositionX / width - 1])) {
                positionX -= velocity;
            } else {
                statusMove = num;
            }
        }
        if (statusMove == 3) {
            if (positionX != realPositionX) {
                positionX += velocity;
            } else if (provisoEnemy(mapArr[realPositionY / height][realPositionX / width + 1])) {
                positionX += velocity;
            } else {
                statusMove = num;
            }
        }
        realPositionX = ((positionX + width / 2) / width) * width;
        realPositionY = ((positionY + height / 2) / height) * height;
    }

    //thuật toán AI move.
    //xE, yE: enemy's position in map[][]
    //xP, yP: player's position in map[][]
    public void AIMove(int[][] mapArr, int xE, int yE, int xP, int yP) {
        BFS bfs = new BFS();
        List<Point> path = new ArrayList<>();
        path = bfs.findPath(mapArr, xE, yE, xP, yP);
        StatusMove statusMove;
        if (path.size() >= 2) {
            if (path.get(1).y * width != positionX) {
                if (path.get(1).y * width > positionX) {
                    statusMove = StatusMove.RIGHT;
                } else {
                    statusMove = StatusMove.LEFT;
                }
            } else {
                if (path.get(1).x * height > positionY) {
                    statusMove = StatusMove.DOWN;
                } else {
                    statusMove = StatusMove.UP;
                }
            }
            handleMove(statusMove, mapArr);
        } else {
            randomMove(map.arrMap);
        }
    }

    //thuật toán handle move.
    public void handleMove(StatusMove statusMove, int[][] mapArr) {
        if (!isDestroy()) {
            if (statusMove.equals(StatusMove.UP)) {
                moveUp(mapArr);
            }
            if (statusMove.equals(StatusMove.DOWN)) {
                moveDown(mapArr);
            }
            if (statusMove.equals(StatusMove.LEFT)) {
                moveLeft(mapArr);
            }
            if (statusMove.equals(StatusMove.RIGHT)) {
                moveRight(mapArr);
            }
            realPositionX = ((positionX + width / 2) / width) * width;
            realPositionY = ((positionY + height / 2) / height) * height;
        }
    }
}
