package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UtilityTool {

    GamePanel gamePanel;
    public UtilityTool(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/" + path + ".png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            return image;
    }

    public BufferedImage loadScaledImage(String path) {
        BufferedImage image = loadImage(path);
        if (image == null) {
            System.err.println("Image not found: " + path);
            return null;
        }
        BufferedImage scaledImage = new BufferedImage(gamePanel.tileSize, gamePanel.tileSize, image.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(image, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.dispose();
        return scaledImage;
    }


}
