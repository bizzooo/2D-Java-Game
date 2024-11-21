package main;

import object.OBJ_KEY;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Font arial_40, arial80Bold;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.000");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial80Bold = new Font("Arial", Font.BOLD, 80);
        OBJ_KEY key = new OBJ_KEY(gamePanel);
        keyImage = key.image;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {

        if(gameFinished){
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            String text;
            int textWidth;
            int x;
            int y;

            text = "You found the Treasure!";
            textWidth = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = gamePanel.screenWidth/2 - textWidth/2;
            y = gamePanel.screenHeight/2 - (gamePanel.tileSize*3);
            g2.drawString(text, x, y);

            text = "Your time is: " + dFormat.format(playTime) + "!";
            textWidth = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gamePanel.screenWidth/2 - textWidth/2;
            y = gamePanel.screenHeight/2 + (gamePanel.tileSize*4);
            g2.drawString(text, x, y);

            g2.setFont(arial80Bold);
            g2.setColor(Color.YELLOW);

            text = "Congratulations!";
            textWidth = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gamePanel.screenWidth/2 - textWidth/2;
            y = gamePanel.screenHeight/2 + (gamePanel.tileSize*2);
            g2.drawString(text, x, y);

            gamePanel.gameThread = null;
        }

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x " + gamePanel.player.hasKey, 74, 61);

        //TIME
        playTime +=(double) 1/60;
        g2.drawString("Time: "+ dFormat.format(playTime), gamePanel.tileSize*11, 61);

        //Draw message
        if(messageOn){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gamePanel.tileSize/2, gamePanel.tileSize*2);
            messageCounter++;
            if (messageCounter > 120) {
                messageOn = false;
                messageCounter = 0;
            }
        }
    }
}
