package main;

import object.OBJ_CHEST;
import object.OBJ_DOOR;
import object.OBJ_KEY;
import object.OBJ_SWORD;

public class ObjectSetter {
    GamePanel gamePanel;

    public ObjectSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        gamePanel.obj[0] = new OBJ_KEY(gamePanel);
        gamePanel.obj[0].worldX = 3 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 3 * gamePanel.tileSize;

        gamePanel.obj[1] = new OBJ_KEY(gamePanel);
        gamePanel.obj[1].worldX = 13 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 15 * gamePanel.tileSize;

        gamePanel.obj[2] = new OBJ_DOOR(gamePanel);
        gamePanel.obj[2].worldX = 5 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 6 * gamePanel.tileSize;

        gamePanel.obj[3] = new OBJ_CHEST(gamePanel);
        gamePanel.obj[3].worldX = 29 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 29 * gamePanel.tileSize;

        gamePanel.obj[4] = new OBJ_DOOR(gamePanel);
        gamePanel.obj[4].worldX = 22 * gamePanel.tileSize;
        gamePanel.obj[4].worldY = 13 * gamePanel.tileSize;

        gamePanel.obj[5] = new OBJ_KEY(gamePanel);
        gamePanel.obj[5].worldX = 29 * gamePanel.tileSize;
        gamePanel.obj[5].worldY = 12 * gamePanel.tileSize;

        gamePanel.obj[6] = new OBJ_KEY(gamePanel);
        gamePanel.obj[6].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[6].worldY = 27 * gamePanel.tileSize;

        gamePanel.obj[7] = new OBJ_DOOR(gamePanel);
        gamePanel.obj[7].worldX = 7 * gamePanel.tileSize;
        gamePanel.obj[7].worldY = 27 * gamePanel.tileSize;

        gamePanel.obj[8] = new OBJ_DOOR(gamePanel);
        gamePanel.obj[8].worldX = 14 * gamePanel.tileSize;
        gamePanel.obj[8].worldY = 27 * gamePanel.tileSize;

        gamePanel.obj[9] = new OBJ_SWORD(gamePanel);
        gamePanel.obj[9].worldX = 12 * gamePanel.tileSize;
        gamePanel.obj[9].worldY = 27 * gamePanel.tileSize;
    }
}
