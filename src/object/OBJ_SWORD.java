package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SWORD extends Entity {
    public OBJ_SWORD(GamePanel gamePanel) {
        super(gamePanel);
        name = "SWORD";
        down1 = loadImage("/resources/objects/sword.png", gamePanel);
    }
}
