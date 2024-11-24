package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class OBJ_HEART extends Entity {
    public OBJ_HEART (GamePanel gamePanel){
        super(gamePanel);
        name = "HEART";
        image = loadImage("/resources/objects/heart_full.png", gamePanel);
        image2 = loadImage("/resources/objects/heart_half.png", gamePanel);
        image3 = loadImage("/resources/objects/heart_empty.png", gamePanel);
    }
}
