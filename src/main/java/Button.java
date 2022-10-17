package main.java;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Button extends Sprite {
    public static final int BUTTON_CIRCLE_SIZE = 70;
    public static final int BUTTON_RECTANGLE_WIDTH = 210;
    public static final int BUTTON_RECTANGLE_HEIGHT = 65;
    public static final int REAL_SIZE = 800;
    public Circle circle;
    public Rectangle rectangle;

    public Button(Image image, int x, int y, int xU, int yU, boolean isCircle) {
        super(image, x, y, xU, yU);
        if (isCircle) {
            super.setWidth(BUTTON_CIRCLE_SIZE);
            super.setHeight(BUTTON_CIRCLE_SIZE);
            super.rWidth = 400;
            super.rHeight = 400;
        }
        else {
            super.setWidth(BUTTON_RECTANGLE_WIDTH);
            super.setHeight(BUTTON_RECTANGLE_HEIGHT);
        }
    }

}