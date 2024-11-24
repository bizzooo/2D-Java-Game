package main;

import entity.Entity;
import entity.NPC_OldMan;
import mob.MON_GreenSlime;
import object.OBJ_CHEST;
import object.OBJ_DOOR;

public class AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        setOBJ(new OBJ_DOOR(gamePanel), 10, 10);
        setOBJ(new OBJ_CHEST(gamePanel), 12, 12);
    }

    public void setNPCs() {
        setNPC(new NPC_OldMan(gamePanel), 7, 7);
        setNPC(new NPC_OldMan(gamePanel), 8, 8);
    }

    public void setMOBs() {
        setMOB(new MON_GreenSlime(gamePanel), 6, 5);
        setMOB(new MON_GreenSlime(gamePanel), 7, 8);
        setMOB(new MON_GreenSlime(gamePanel), 8, 9);
    }

    public void setNPC(Entity npc, int xCord, int yCord) {
        for (int i = 0; i < gamePanel.npc.length; i++) {
            if(gamePanel.npc[i] == null) {
                gamePanel.npc[i] = npc;
                gamePanel.npc[i].worldX = gamePanel.tileSize*xCord;
                gamePanel.npc[i].worldY = gamePanel.tileSize*yCord;
                return;
            }
        }
    }

    public void setOBJ(Entity obj, int xCord, int yCord) {
        for (int i = 0; i < gamePanel.obj.length; i++) {
            if(gamePanel.obj[i] == null) {
                gamePanel.obj[i] = obj;
                gamePanel.obj[i].worldX = gamePanel.tileSize*xCord;
                gamePanel.obj[i].worldY = gamePanel.tileSize*yCord;
                return;
            }
        }
    }

    public void setMOB(Entity mob, int xCord, int yCord) {
        for (int i = 0; i < gamePanel.mob.length; i++) {
            if(gamePanel.mob[i] == null) {
                gamePanel.mob[i] = mob;
                gamePanel.mob[i].worldX = gamePanel.tileSize*xCord;
                gamePanel.mob[i].worldY = gamePanel.tileSize*yCord;
                return;
            }
        }
    }
}
