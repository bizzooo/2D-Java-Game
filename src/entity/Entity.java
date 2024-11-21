package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle collisionBox;
    public int collisionBoxDefaultX, collisionBoxDefaultY;

    public boolean collisionOn = false;
    public boolean collisionUp = false;
    public boolean collisionDown = false;
    public boolean collisionLeft = false;
    public boolean collisionRight = false;
}
