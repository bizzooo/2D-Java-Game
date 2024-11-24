package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    int idleCounter = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        collisionBox = new Rectangle(8, 8, gamePanel.tileSize - 16, gamePanel.tileSize - 16);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        maxHealth = 10;
        health = maxHealth;

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

    public void update() {

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.ePressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            }
            if (keyHandler.downPressed) {
                direction = "down";
            }
            if (keyHandler.leftPressed) {
                direction = "left";
            }
            if (keyHandler.rightPressed) {
                direction = "right";
            }

            // CHECK COLLISION
            gamePanel.collisionHandler.checkTile(this); //check tile collision

            int objIndex = gamePanel.collisionHandler.checkObject(this, true); //pickup object if any
            pickUpObject(objIndex);

            int npcIndex = gamePanel.collisionHandler.checkEntity(this, gamePanel.npc); //check entity collision
            interactNPC(npcIndex);

            int mobIndex = gamePanel.collisionHandler.checkEntity(this, gamePanel.mob); //check mob collision
            contactMob(mobIndex);

            //CHECK EVENT
            gamePanel.eventHandler.checkEvent();

            // MOVE PLAYER
            if (direction.equals("up") && !collisionUp && worldY - speed >= 0 && !keyHandler.ePressed) {
                worldY -= speed;
            }
            if (direction.equals("down") && !collisionDown && worldY + speed < gamePanel.worldHeight - gamePanel.tileSize && !keyHandler.ePressed) {
                worldY += speed;
            }
            if (direction.equals("left") && !collisionLeft && worldX - speed >= 0 && !keyHandler.ePressed) {
                worldX -= speed;
            }
            if (direction.equals("right") && !collisionRight && worldX + speed < gamePanel.worldWidth - gamePanel.tileSize && !keyHandler.ePressed) {
                worldX += speed;
            }

                spriteCounter++;
                if (spriteCounter > 12 && !keyHandler.ePressed) {
                    if (spriteNumber == 1) {
                        spriteNumber = 2;
                    } else if (spriteNumber == 2) {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            }
        else{
                idleCounter++;
                if (idleCounter > 20) {
                    spriteNumber = 1;
                    idleCounter = 0;
                }
            }
            if(invulnrable){
                invulnrableCounter++;
                if(invulnrableCounter > 60){
                    invulnrable = false;
                    invulnrableCounter = 0;
                }
            }
        }

        public void pickUpObject(int i){
            if (i != 999) {

            }
        }

    public void draw(Graphics2D g2) {
        BufferedImage playerImage = getImageForDirection(direction, spriteNumber);;

        int x = screenX;
        int y = screenY;
        if(screenX > worldX){
            x = worldX;
        }
        if(screenY > worldY){
            y = worldY;
        }
        int rightOffset = gamePanel.screenWidth - screenX;
        if(rightOffset > gamePanel.worldWidth - worldX){
            x = gamePanel.screenWidth - (gamePanel.worldWidth - worldX);
        }
        int bottomOffset = gamePanel.screenHeight - screenY;
        if(bottomOffset > gamePanel.worldHeight - worldY){
            y = gamePanel.screenHeight - (gamePanel.worldHeight - worldY);
        }

        if(invulnrable){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Set transparency if invulnerable
        }
        g2.drawImage(playerImage, x, y, null); // Draw player
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset transparency

        if(gamePanel.debug)renderDebug(g2);
    }

    public void interactNPC(int i){
        if(i != 999){
            if (gamePanel.keyHandler.ePressed) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[i].speak();
            };
        }
    }

    public void contactMob(int i){
        if(i != 999){
            if(!invulnrable) {
                health -= 1;
                invulnrable = true;
            }
        }
    }

    public void renderDebug(Graphics2D g2) {

        g2.setColor(Color.RED);
        g2.drawRect(screenX + collisionBox.x, screenY + collisionBox.y, collisionBox.width, collisionBox.height);
    }
}

