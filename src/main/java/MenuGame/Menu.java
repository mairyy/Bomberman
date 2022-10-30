package MenuGame;

import javafx.scene.canvas.GraphicsContext;

public abstract class Menu {
    public abstract void load();
    public abstract void render(GraphicsContext gc);
    public abstract void clear();
}
