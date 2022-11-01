package gamePlay.entity.Item;

import gamePlay.Map;
import gamePlay.entity.Entity;

import java.sql.Struct;

public class Item extends Entity {
    //nhận đầu vào là map có các thông số game như player, enemy,...
    protected Map map;

    public void update(){;}
    public boolean checkColling() {
        if (map.player.getRealPositionX() == positionX && map.player.getRealPositionY() == positionY
        && map.arrMap[positionY/height][positionX/width] == 1) {
            return true;
        }
        return false;
    }
}
