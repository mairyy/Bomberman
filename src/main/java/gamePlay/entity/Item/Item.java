package main.entity.Item;

import main.java.gamePlay.Map;
import main.java.gamePlay.entity.Entity;

public class Item extends Entity {
    protected Map map;
    private double timeEffect;

    public void setTimeEffect(double timeEffect) {
        this.timeEffect = timeEffect;
    }

    public double getTimeEffect() {
        return timeEffect;
    }

    public void update(){;}
}
