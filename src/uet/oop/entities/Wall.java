package uet.oop.entities;

import javafx.scene.canvas.GraphicsContext;

public class Wall extends Entity{

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY, width, height);
    }
}
