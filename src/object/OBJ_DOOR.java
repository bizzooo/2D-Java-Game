package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_DOOR extends Entity {
    public OBJ_DOOR(GamePanel gamePanel) {
        super(gamePanel);
        name = "DOOR";
        down1 = loadImage("/resources/objects/door.png", gamePanel);
        collision = true;

        // Set the door collision box
        setCollisionBox(0,16,48,32);
    }
}
