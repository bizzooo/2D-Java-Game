package object;

import main.GamePanel;

public class OBJ_KEY extends SuperObject {
    public OBJ_KEY(GamePanel gamePanel) {
        name = "KEY";
        try {
            image = loadImage("/resources/objects/key.png", gamePanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
