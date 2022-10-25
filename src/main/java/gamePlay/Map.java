package main.java.gamePlay;

import javafx.scene.input.KeyCode;
import main.java.gamePlay.entity.*;
import javafx.scene.canvas.GraphicsContext;
import main.java.gamePlay.entity.Entity;
import main.entity.Item.AddBom;
import main.entity.Item.Item;
import main.entity.Item.PowerUpBom;
import main.entity.Item.PowerUpSpeed;
import main.java.gamePlay.GamePlay;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    private int lenWidth;
    private int lenHeight;
    private Background background = new Background();
    public HashMap<Integer, Entity> walls = new HashMap<>();
    public List<Enemy> enemies = new ArrayList<>();
    public List<Bom> boms = new ArrayList<>();
    public Teleport teleport;
    public int[][] arrMap;
    public Player player = new Player();
    private List<Item> items = new ArrayList<>();

    private int powerBom = 1;

    public int getLenHeight() {
        return lenHeight;
    }

    public int getLenWidth() {
        return lenWidth;
    }

    public int getPowerBom() {
        return powerBom;
    }

    public void setPowerBom(int powerBom) {
        this.powerBom = powerBom;
    }

    public void loadMap(File file) {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
            String line = inputStream.readLine();
            String[] arr = line.split(" ");
            lenWidth = Integer.parseInt(arr[0]);
            lenHeight = Integer.parseInt(arr[1]);
            arrMap = new int[lenHeight][lenWidth];
            for (int i = 0; i < lenHeight; i++) {
                line = inputStream.readLine();
                arr = line.split(" ");
                for (int j = 0; j < lenWidth; j++) {
                    int tmp = Integer.parseInt(arr[j]);
                    switch (tmp) {
                        case 00:
                            walls.put(j*lenWidth*10 + i, new Wall(j * GamePlay.widthUnit, i * GamePlay.widthUnit));
                            arrMap[i][j] = 0;
                            break;
                        case 02:
                            walls.put(j*lenWidth*10 + i ,new Brick(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            arrMap[i][j] = 2;
                            break;
                        case 03:
                            player.setPosition(j * GamePlay.widthUnit, i * GamePlay.widthUnit);
                            arrMap[i][j] = 1;
                            break;
                        case 04:
                            enemies.add(new Enemy(j * GamePlay.widthUnit, i * GamePlay.widthUnit));
                            arrMap[i][j] = 1;
                            break;
                        case 07:
                            walls.put(j*lenWidth*10 + i ,new Brick(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            arrMap[i][j] = 2;
                            items.add(new PowerUpSpeed(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            break;
                        case 8:
                            walls.put(j*lenWidth*10 + i ,new Brick(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            arrMap[i][j] = 2;
                            items.add(new AddBom(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            break;
                        case 9:
                            walls.put(j*lenWidth*10 + i ,new Brick(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            arrMap[i][j] = 2;
                            items.add(new PowerUpBom(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            break;
                        case 10:
                            walls.put(j*lenWidth*10 + i ,new Brick(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this));
                            arrMap[i][j] = 2;
                            teleport = new Teleport(j * GamePlay.widthUnit, i * GamePlay.widthUnit, this);
                            break;
                        default:
                            arrMap[i][j] = 1;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(double time, List<KeyCode> events) {
        handleInput(events);
        if (boms.size() != 0) {
            for (int i = 0; i < boms.size(); i++) {
                boms.get(i).setFrame(time);
                if (boms.get(i).isEnd()) {
                    arrMap[boms.get(i).getPositionY() / boms.get(i).getHeight()][boms.get(i).getPositionX() / boms.get(i).getHeight()] = 1;
                    boms.remove(i);
                    i--;
                    continue;
                }
                boms.get(i).checkCollision();
            }
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).update();
            if(items.get(i).isDestroy()){
                items.remove(i);
                i--;
            }
        }
        teleport.update();

        List<Integer> toRemove = new ArrayList<>();
        for (Entity wall : walls.values()) {
            if (wall instanceof Brick) {
                ((Brick) wall).update(toRemove);
            }
        }
        for (int i : toRemove) {
            walls.remove(i);
        }
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0,0, 800, 800);
        background.render(gc, GamePlay.widthScreen, GamePlay.heightScreen);
        teleport.render(gc);
        for (Item item : items) {
            item.render(gc);
        }
        for (Entity wall : walls.values()) {
            wall.render(gc);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if(!enemies.get(i).isEndGame()) {
                enemies.get(i).move(arrMap);
                enemies.get(i).render(gc);
                if (enemies.get(i).isColling(player)) {
                    player.setDestroy(true);
                }
            } else {
                enemies.remove(i);
                i--;
            }
        }
        if (boms.size() != 0) {
            for (Bom bom : boms) {
                bom.render(gc);
            }
        }
        if (!player.isEndGame()) {
            player.render(gc);
        }
    }

    public void handleInput(List<KeyCode> events) {
        player.setAnimations(events);
        if (events.contains(KeyCode.SPACE) &&
                arrMap[player.getRealPositionY() / player.getHeight()][player.getRealPositionX() / player.getWidth()] != 6
                 && player.getMaxTotalBom() > boms.size() &&
                !player.isDestroy()) {
            Bom bom = new Bom(player.getRealPositionX(), player.getRealPositionY(), this);
            arrMap[bom.getPositionY() / GamePlay.widthUnit][bom.getPositionX() / GamePlay.widthUnit] = 6;
            boms.add(bom);
        }
        player.handleMove(events, arrMap);
    }
}
