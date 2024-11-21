package object;

import main.GamePanel;

public class OBJ_CHEST extends SuperObject {
    public OBJ_CHEST(GamePanel gamePanel) {
        name = "CHEST";
        try {
            image = loadImage("/resources/objects/chest.png", gamePanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
