package main.java;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public abstract class Entity {
    Image image;
    int positionX;
    int positionY;
    int width;
    int height;

    public Entity() {
        positionX = 0;
        positionY = 0;
        width = 0;
        height = 0;
    }

    /**
     * setter and getter.
     */
    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void loadImage(String path) {
        image = new Image(path);
        width = (int) image.getWidth();
        height = (int) image.getHeight();
    }

    public abstract void render(GraphicsContext gc);

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean isColling(Entity other) {
        return this.getBoundary().intersects(other.getBoundary());
    }
}
