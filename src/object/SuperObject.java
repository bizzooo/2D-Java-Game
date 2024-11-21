package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    public int collisionBoxDefaultX = 0;
    public int collisionBoxDefaultY = 0;


    public BufferedImage loadImage(String path, GamePanel gamepanel) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            BufferedImage scaledImage = new BufferedImage(gamepanel.tileSize, gamepanel.tileSize, image.getType());
            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(image, 0, 0, gamepanel.tileSize, gamepanel.tileSize, null);
            g2.dispose();
            return scaledImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2, GamePanel gamePanel){
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if(worldX + gamePanel.tileSize> gamePanel.player.worldX - gamePanel.player.screenX &&
           worldX - gamePanel.tileSize< gamePanel.player.worldX + gamePanel.player.screenX &&
           worldY + gamePanel.tileSize> gamePanel.player.worldY - gamePanel.player.screenY &&
           worldY - gamePanel.tileSize< gamePanel.player.worldY + gamePanel.player.screenY) {

                g2.drawImage(image, screenX, screenY,null);
        }
    }
}
