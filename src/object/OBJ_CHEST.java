package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_CHEST extends Entity {
    public OBJ_CHEST(GamePanel gamePanel) {
        super(gamePanel);

        name = "CHEST";
        down1 = loadImage("/resources/objects/chest.png", gamePanel);
    }
}
