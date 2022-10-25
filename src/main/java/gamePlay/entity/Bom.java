package main.java.gamePlay.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.java.gamePlay.GamePlay;
import main.java.gamePlay.Map;
import main.utils.ImageUtils;

import java.io.File;

public class Bom extends Entity {
    private final int numberFrame = 4;

    private final int numberFameFire = 5;
    private final Image[] animations = new Image[numberFrame];
    private final Image[][] explodeImage = new Image[numberFameFire][7];
    private double timeBom = 2;
    private double frame = 0;
    private boolean isExplode = false;
    private boolean isEnd = false;
    private Map map;
    final MediaPlayer soundBom;


    public void setTimeBom(double timeBom) {
        this.timeBom = timeBom;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setExplode(boolean explode) {
        isExplode = explode;
    }

    public boolean isExplode() {
        return isExplode;
    }

    public Bom(int positionX, int positionY, Map map) {
        loadImage("resource/image/bombs.png");
        width = (int) image.getWidth() / 3;
        height = (int) image.getHeight();
        for (int i = 0; i < 3; i++) {
            Image newImage = ImageUtils.crop(image, i * width, 0, width, height);
            animations[i] = newImage;
        }
        animations[3] = animations[1];
        loadImage("resource/image/fires.png");
        width = (int) ((image.getWidth()-3*6) / 7);
        height = (int) ((image.getHeight()-3*4) / numberFameFire);
        int kc = 3;
        for (int i = 0; i < numberFameFire; i++) {
            for (int j = 0; j < 7; j++) {
                Image newImage = ImageUtils.crop(image, j * (width + kc), i * (height+kc), width, height);
                explodeImage[i][j] = newImage;
            }
        }
        this.positionX = positionX;
        this.positionY = positionY;
        width = GamePlay.widthUnit;
        height = GamePlay.widthUnit;
        this.map = map;
        Media newSound = new Media(new File("res/resource/music/Sounds/Bombs/Battle Mode Sounds (41).wav").toURI().toString());
        soundBom = new MediaPlayer(newSound);
    }

    public void render(GraphicsContext gc) {
        if (!isExplode) {
            gc.drawImage(animations[(int) frame], positionX, positionY, width, height);
        } else {
            gc.drawImage(explodeImage[(int) frame][0], positionX, positionY, width, height);
            //left
            for(int i = 1; i <= map.getPowerBom(); i++) {
                if(i == map.getPowerBom()) {
                    if(map.arrMap[positionY/height][positionX/width-i] == 1) {
                        gc.drawImage(explodeImage[(int) frame][6], positionX - width*i, positionY, width, height);
                    }
                } else if(map.arrMap[positionY/height][positionX/width-i] == 1) {gc.drawImage(explodeImage[(int) frame][4], positionX - width*i, positionY, width, height);
                } else {
                    break;
                }
            }
            //right
            for(int i = 1; i <= map.getPowerBom(); i++) {
                if(i == map.getPowerBom()) {
                    if(map.arrMap[positionY/height][positionX/width+i] == 1) {
                        gc.drawImage(explodeImage[(int) frame][5], positionX + width*i, positionY, width, height);
                    }
                } else if(map.arrMap[positionY/height][positionX/width+i] == 1) {gc.drawImage(explodeImage[(int) frame][4], positionX + width*i, positionY, width, height);
                } else {
                    break;
                }
            }
            //up
            for(int i = 1; i <= map.getPowerBom(); i++) {
                if(i == map.getPowerBom()) {
                    if(map.arrMap[positionY/height-i][positionX/width] == 1) {
                        gc.drawImage(explodeImage[(int) frame][3], positionX, positionY-height*i, width, height);
                    }
                } else if(map.arrMap[positionY/height-i][positionX/width] == 1) {gc.drawImage(explodeImage[(int) frame][1], positionX, positionY - height*i, width, height);
                } else {
                    break;
                }
            }
            //down
            for(int i = 1; i <= map.getPowerBom(); i++) {
                if(i == map.getPowerBom()) {
                    if(map.arrMap[positionY/height+i][positionX/width] == 1) {
                        gc.drawImage(explodeImage[(int) frame][2], positionX, positionY+height*i, width, height);
                    }
                } else if(map.arrMap[positionY/height+i][positionX/width] == 1) {gc.drawImage(explodeImage[(int) frame][1], positionX, positionY + height*i, width, height);
                } else {
                    break;
                }
            }
        }
    }

    public void setFrame(double time) {
        if (!isExplode) {
            frame += time * 5;
            if (frame >= numberFrame) {
                frame = 0;
            }
            timeBom -= time;
            if (timeBom <= 0) {
                isExplode = true;
                frame = 0;
            }
        } else {
            frame += time * 10;
            if(frame >= 5) {
                isEnd = true;
            }
        }
    }
    public void checkCollision() {
        if (isExplode) {
            handleCollision(positionY/height, positionX/width);
            //left
            for(int i = 1; i <= map.getPowerBom(); i++) {
                handleCollision(positionY/height, positionX/width-i);
                if(map.arrMap[positionY/height][positionX/width-i] != 1) {
                    break;
                }
            }
            //right
            for(int i = 1; i <= map.getPowerBom(); i++) {
                handleCollision(positionY/height, positionX/width+i);
                if(map.arrMap[positionY/height][positionX/width+i] != 1) {
                    break;
                }
            }
            //up
            for(int i = 1; i <= map.getPowerBom(); i++) {
                handleCollision(positionY/height-i, positionX/width);
                if(map.arrMap[positionY/height-i][positionX/width] != 1) {
                    break;
                }
            }
            //down
            for(int i = 1; i <= map.getPowerBom(); i++) {
                handleCollision(positionY/height+i, positionX/width);
                if(map.arrMap[positionY/height+i][positionX/width] != 1) {
                    break;
                }
            }
        }
    }

    public void handleCollision(int posY, int posX) {
        //brick
        if(map.arrMap[posY][posX] == 2) {
            Integer i = posY + posX* map.getLenWidth()*10;
            map.walls.get(i).setDestroy(true);
        }
        if(map.player.getRealPositionX() == posX*width && map.player.getRealPositionY() == posY*height) {
            map.player.setDestroy(true);
        }
        for(int i = 0; i < map.enemies.size(); i++) {
            if(map.enemies.get(i).getRealPositionX() == posX*width && map.enemies.get(i).getRealPositionY() == posY*height) {
                map.enemies.get(i).setDestroy(true);
            }
        }
        for(int i = 0; i < map.boms.size(); i++) {
            if(map.boms.get(i).positionX == posX * width && map.boms.get(i).positionY == posY * height) {
                map.boms.get(i).timeBom = 0;
            }
        }
    }
    public void playSound() {
        if(isExplode) {
            soundBom.play();
        }
    }
}
