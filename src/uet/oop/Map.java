package uet.oop;

import javafx.scene.input.KeyCode;
import main.entity.*;
import javafx.scene.canvas.GraphicsContext;
import main.entity.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private int lenWidth;
    private int lenHeight;
    private Background background = new Background();
    private List<Entity> walls = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private int[][] arrMap;

    public int getLenHeight() {
        return lenHeight;
    }

    public int getLenWidth() {
        return lenWidth;
    }


    private Player player = new Player();
    public void loadMap(File file) {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))){
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
                            walls.add(new Wall(j*Main.widthUnit, i*Main.widthUnit));
                            arrMap[i][j] = 0;
                            break;
                        case 02:
                            walls.add(new Brick(j*Main.widthUnit, i*Main.widthUnit));
                            arrMap[i][j] = 2;
                            break;
                        case 03:
                            player.setPosition(j*Main.widthUnit, i*Main.widthUnit);
                            arrMap[i][j] = 1;
                            break;
                        case 04:
                            enemies.add(new Enemy(j*Main.widthUnit, i*Main.widthUnit));
                            arrMap[i][j] = 1;
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

    public void render(GraphicsContext gc) {
        background.render(gc, Main.widthScreen, Main.heightScreen);
        for(Entity wall : walls) {
            wall.render(gc);
        }
        for(Enemy enemy : enemies) {
            enemy.move(walls);
            enemy.render(gc);
            if (enemy.isColling(player)) {
                player.setDie(true);
            }
        }
        if(!player.isEndGame()) {
            player.render(gc);
        }
    }

    public void handleInput(List<KeyCode> events) {
        player.setAnimations(events);
        if(events.contains(KeyCode.SPACE)) {
            Entity bom = new Bom(player.getRealPositionX(), player.getRealPositionY());
            walls.add(bom);
            arrMap[bom.getPositionY()/Main.widthUnit][bom.getPositionX()/Main.widthUnit] = 6;
        }
        player.handleMove(events, arrMap);
    }

}
