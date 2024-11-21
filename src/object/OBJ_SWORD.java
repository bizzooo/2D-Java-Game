package object;

import main.GamePanel;

public class OBJ_SWORD extends SuperObject {
    public OBJ_SWORD(GamePanel gamePanel) {
        name = "SWORD";
        try {
            image = loadImage("/resources/objects/sword.png", gamePanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
