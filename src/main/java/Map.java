package main.java;

import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    int lenWidth;
    int lenHeight;
    public Entity[][] entities = new Entity[100][100];
    public void loadMap(File file) {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))){
            String line = inputStream.readLine();
            String[] arr = line.split(" ");
            lenWidth = Integer.parseInt(arr[0]);
            lenHeight = Integer.parseInt(arr[1]);
            System.out.println( lenWidth +  " " + lenHeight);
            entities = new Entity[lenHeight][lenWidth];
            for (int i = 0; i < lenHeight; i++) {
                line = inputStream.readLine();
                arr = line.split(" ");
                for (int j = 0; j < lenWidth; j++) {
                    int tmp = Integer.parseInt(arr[j]);
                    switch (tmp) {
                        case 00:
                            Entity wall = new Wall();
                            wall.loadImage("resource/sprites/wall.png");
                            wall.setPosition(j*Main.widthUnit, i*Main.widthUnit);
                            wall.setWidth(Main.widthUnit);
                            wall.setHeight(Main.widthUnit);
                            entities[i][j] = wall;
                            break;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < lenHeight; i++) {
            for (int j = 0; j < lenWidth; j++) {
                entities[i][j].render(gc);
            }
        }
    }

}
