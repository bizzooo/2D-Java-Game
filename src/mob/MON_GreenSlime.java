package mob;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxHealth = 4;
        health = maxHealth;

        setCollisionBox(3,18,42,30);

        up1 = loadImage("/resources/mob/slime1.png", gamePanel);
        up2 = loadImage("/resources/mob/slime2.png", gamePanel);
        down1 = loadImage("/resources/mob/slime1.png", gamePanel);
        down2 = loadImage("/resources/mob/slime2.png", gamePanel);
        left1 = loadImage("/resources/mob/slime1.png", gamePanel);
        left2 = loadImage("/resources/mob/slime2.png", gamePanel);
        right1 = loadImage("/resources/mob/slime1.png", gamePanel);
        right2 = loadImage("/resources/mob/slime2.png", gamePanel);
    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1~100
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
}
