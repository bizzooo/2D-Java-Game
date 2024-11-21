package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int idleCounter = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;
        screenY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;

        collisionBox = new Rectangle(8,8,gamePanel.tileSize -16,gamePanel.tileSize -16);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gamePanel.tileSize * 15;
        worldY = gamePanel.tileSize * 15;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setupImage("player/up1");
        up2 = setupImage("player/up2");
        down1 = setupImage("player/down1");
        down2 = setupImage("player/down2");
        left1 = setupImage("player/left1");
        left2 = setupImage("player/left2");
        right1 = setupImage("player/right1");
        right2 = setupImage("player/right2");
    }

    public BufferedImage setupImage(String imageName){
        UtilityTool uTool = new UtilityTool(gamePanel);
        return uTool.loadScaledImage(imageName);

    }

    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if(keyHandler.upPressed){
                direction = "up";
            }
            if(keyHandler.downPressed){
                direction = "down";
            }
            if(keyHandler.leftPressed){
                direction = "left";
            }
            if(keyHandler.rightPressed){
                direction = "right";
            }
            // Check collision
            gamePanel.collisionHandler.checkTile(this);
            // Check object collision
            int objIndex = gamePanel.collisionHandler.checkObject(this,true);
            pickUpObject(objIndex);
            // Move player if no collision in the direction
            if (keyHandler.upPressed && !collisionUp && !collisionOn && worldY - speed >= 0) {
                worldY -= speed;
            }
            if (keyHandler.downPressed && !collisionDown && !collisionOn && worldY + speed < gamePanel.worldHeight - gamePanel.tileSize) {
                worldY += speed;
            }
            if (keyHandler.leftPressed && !collisionLeft && !collisionOn && worldX - speed >= 0) {
                worldX -= speed;
            }
            if (keyHandler.rightPressed && !collisionRight && !collisionOn && worldX + speed < gamePanel.worldWidth - gamePanel.tileSize) {
                worldX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            idleCounter++;
            if (idleCounter > 20) {
                spriteNumber = 1;
                idleCounter = 0;
            }
        }
    }

    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gamePanel.obj[i].name;

            switch (objectName){
                case "KEY":
                    hasKey++;
                    gamePanel.obj[i] = null;
                    gamePanel.playSoundEffect(1);
                    gamePanel.ui.showMessage("You have found a key!");
                    break;
                case "DOOR":
                    if(hasKey > 0){
                        hasKey--;
                        gamePanel.obj[i] = null;
                        gamePanel.ui.showMessage("You have opened the door!");
                        gamePanel.playSoundEffect(2);
                    } else if (hasKey == 0){ // If player has no key
                        gamePanel.ui.showMessage("You need a key to open this door!");
                    }
                    break;
                case "SWORD":
                    gamePanel.obj[i] = null;
                    speed += 2;
                    gamePanel.playSoundEffect(4);
                    gamePanel.ui.showMessage("Your speed has increased!");
                    break;
                case "CHEST":
                    gamePanel.obj[i] = null;
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(3);
                    break;
            }
        }
    }

    public void render(Graphics2D g2) {
        BufferedImage playerImage = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    playerImage = up1;
                }
                if (spriteNumber == 2) {
                    playerImage = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    playerImage = down1;
                }
                if (spriteNumber == 2) {
                    playerImage = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    playerImage = left1;
                }
                if (spriteNumber == 2) {
                    playerImage = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    playerImage = right1;
                }
                if (spriteNumber == 2) {
                    playerImage = right2;
                }
                break;
        }
        g2.drawImage(playerImage, screenX, screenY, null);
    }
}

