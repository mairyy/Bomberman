package gamePlay.entity.Item;

import gamePlay.Map;
import gamePlay.entity.Entity;

import java.sql.Struct;

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
    public boolean checkColling() {
        if (map.player.getRealPositionX() == positionX && map.player.getRealPositionY() == positionY
        && map.arrMap[positionY/height][positionX/width] == 1) {
            return true;
        }
        return false;
    }
}
