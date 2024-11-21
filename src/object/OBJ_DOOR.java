package object;

import main.GamePanel;

public class OBJ_DOOR extends SuperObject {
    public OBJ_DOOR(GamePanel gamePanel) {
        name = "DOOR";
        try {
            image = loadImage("/resources/objects/door.png", gamePanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
