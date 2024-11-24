package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_KEY extends Entity {
    public OBJ_KEY(GamePanel gamePanel) {
        super(gamePanel);
        name = "KEY";
        down1 = loadImage("/resources/objects/key.png", gamePanel);
    }
}
