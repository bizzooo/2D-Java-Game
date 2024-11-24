package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);
        getImage();
        setDialogue();
        direction = "down";
        speed = 1;
    }

    public void getImage() {
        up1 = setupImage("NPC/oldman_up_1");
        up2 = setupImage("NPC/oldman_up_2");
        down1 = setupImage("NPC/oldman_down_1");
        down2 = setupImage("NPC/oldman_down_2");
        left1 = setupImage("NPC/oldman_left_1");
        left2 = setupImage("NPC/oldman_left_2");
        right1 = setupImage("NPC/oldman_right_1");
        right2 = setupImage("NPC/oldman_right_2");
    }

    public void setDialogue(){
        dialogues[0] = "Hello!";
        dialogues[1] = "I am the great old man bizgo";
        dialogues[2] = "I have been waiting for you all this time in this \nlittle village. THIS IS THE END OF THE GAME!";
    }
    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 240) {
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

    public void speak(){
        super.speak();
    }
}
