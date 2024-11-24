package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public GamePanel gamePanel;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    public int collisionBoxDefaultX, collisionBoxDefaultY;
    public int actionLockCounter = 0;
    String[] dialogues = new String[20];
    public boolean invulnrable = false;
    public int invulnrableCounter = 0;
    int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = player, 1 = npc, 2 = mob, 3 = object

    //COLLISION BOOLEANS
    public boolean collisionUp = false;
    public boolean collisionDown = false;
    public boolean collisionLeft = false;
    public boolean collisionRight = false;

    //CHARACTER STATUS
    public int maxHealth;
    public int health;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public BufferedImage setupImage(String imagePath){
        UtilityTool uTool = new UtilityTool(gamePanel);
        return uTool.loadScaledImage(imagePath);
    }
    public void setAction(){

    }
    public void speak(){
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        switch (gamePanel.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update(){
        setAction();
        gamePanel.collisionHandler.checkTile(this);
        gamePanel.collisionHandler.checkObject(this, false);
        gamePanel.collisionHandler.checkEntity(this, gamePanel.npc);
        gamePanel.collisionHandler.checkEntity(this, gamePanel.mob);
        boolean contactPlayer = gamePanel.collisionHandler.checkPlayerCollision(this);

        if(this.type == 2 && contactPlayer){
            if(!gamePanel.player.invulnrable){
                gamePanel.player.health--;
                gamePanel.player.invulnrable = true;
            }
        }

        if (direction.equals("up") && !collisionUp && worldY - speed >= 0) {
            worldY -= speed;
        }
        if (direction.equals("down") && !collisionDown && worldY + speed < gamePanel.worldHeight - gamePanel.tileSize) {
            worldY += speed;
        }
        if (direction.equals("left") && !collisionLeft && worldX - speed >= 0) {
            worldX -= speed;
        }
        if (direction.equals("right") && !collisionRight && worldX + speed < gamePanel.worldWidth - gamePanel.tileSize) {
            worldX += speed;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (gamePanel.player.screenX > gamePanel.player.worldX) {
            screenX = worldX;
        }
        if (gamePanel.player.screenY > gamePanel.player.worldY) {
            screenY = worldY;
        }
        // Stop moving the camera at the edge of the map
        int rightOffset = gamePanel.screenWidth - gamePanel.player.screenX;
        if (rightOffset > gamePanel.worldWidth - gamePanel.player.worldX) {
            screenX = gamePanel.screenWidth - (gamePanel.worldWidth - worldX);
        }
        int bottomOffset = gamePanel.screenHeight - gamePanel.player.screenY;
        if (bottomOffset > gamePanel.worldHeight - gamePanel.player.worldY) {
            screenY = gamePanel.screenHeight - (gamePanel.worldHeight - worldY);
        }

        if (worldX + gamePanel.tileSize > 0 &&
                worldX < gamePanel.worldWidth &&
                worldY + gamePanel.tileSize > 0 &&
                worldY < gamePanel.worldHeight) {

            image = getImageForDirection(direction, spriteNumber);

            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

            if(gamePanel.debug)renderDebug(g2);
        }
    }

    public BufferedImage getImageForDirection(String direction, int spriteNumber) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                } else if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                } else if (spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                }
                break;
        }
        return image;
    }
    public void renderDebug(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawRect(worldX - gamePanel.player.worldX + gamePanel.player.screenX + collisionBox.x,
                worldY - gamePanel.player.worldY + gamePanel.player.screenY + collisionBox.y,
                collisionBox.width, collisionBox.height);
    }

    public BufferedImage loadImage(String path, GamePanel gamepanel) {
        try {
            BufferedImage rawImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            BufferedImage scaledImage = new BufferedImage(gamepanel.tileSize, gamepanel.tileSize, rawImage.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(rawImage, 0, 0, gamepanel.tileSize, gamepanel.tileSize, null);
            g2.dispose();
            return scaledImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCollisionBox(int x, int y, int width, int height){
        collisionBox.x = x;
        collisionBox.y = y;
        collisionBox.width = width;
        collisionBox.height = height;
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;
    }
}
