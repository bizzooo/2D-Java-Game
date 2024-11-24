package main;

import entity.Entity;
import object.OBJ_HEART;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    Font maruMonica;
    BufferedImage heart_full, heart_half, heart_empty;
    public int commandNumber = 0;
    public int titleScreenState = 0; //0 = First Screen etc etc

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.000");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        InputStream is = getClass().getResourceAsStream("/resources/fonts/MaruMonica.ttf");
        try {
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Entity heart = new OBJ_HEART(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        //TITLESTATE
        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        }
        //PLAYSTATE
        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayerLife();
        }
        //PAUSESTATE
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    public void drawPlayerLife() {
        int x = gamePanel.tileSize / 2; // Start position for hearts
        int y = gamePanel.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE (empty hearts)
        // Draw empty hearts first
        while (i < gamePanel.player.maxHealth / 2) {
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        // Draw full and half hearts next
        x = gamePanel.tileSize / 2;
        i = 0;

        while (i < gamePanel.player.health) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gamePanel.player.health){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }
    }

    public void drawTitleScreen() {
        if(titleScreenState == 0) {
            //BACKGROUND SHADOW
            g2.setFont((g2.getFont().deriveFont(Font.BOLD, 100F)));
            g2.setColor(Color.GRAY);
            String text = "ADVENTURE GAME";
            int x = getXforCenteredText(text) + 5;
            int y = gamePanel.tileSize * 3 + 5;
            g2.drawString(text, x, y);

            //TITLE NAME
            text = "ADVENTURE GAME";
            x = getXforCenteredText(text);
            y = gamePanel.tileSize * 3;
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //PLAYER CHARACTER
            x = gamePanel.screenWidth / 2 - gamePanel.tileSize;
            y += gamePanel.tileSize * 2;
            g2.drawImage(gamePanel.player.getImageForDirection("down", 1), x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

            //MENU
            g2.setFont((g2.getFont().deriveFont(Font.PLAIN, 40F)));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += (int) (gamePanel.tileSize * 3.5);
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }
            g2.setFont((g2.getFont().deriveFont(Font.PLAIN, 40F)));
            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }

            g2.setFont((g2.getFont().deriveFont(Font.PLAIN, 40F)));
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize;
            g2.setColor(Color.YELLOW);
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            //CLASS SELECTION
            g2.setColor(Color.WHITE);
            g2.setFont((g2.getFont().deriveFont(60F)));

            String text = "SELECT YOUR CLASS";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize * 3;
            g2.drawString(text, x, y);

            g2.setFont((g2.getFont().deriveFont(42F)));
            text = "WARRIOR";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }
            text = "SORCERER";
            x = getXforCenteredText(text);
            y += (int) (gamePanel.tileSize * 1.5);
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }
            text = "THIEF";
            x = getXforCenteredText(text);
            y += (int) (gamePanel.tileSize * 1.5);
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }
            text = "BACK";
            x = getXforCenteredText(text);
            y += (int) (gamePanel.tileSize * 2);
            g2.drawString(text, x, y);
            if (commandNumber == 3) {
                g2.drawString(">", x - gamePanel.tileSize, y);
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(maruMonica);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        //WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - gamePanel.tileSize * 4;
        int height = gamePanel.tileSize * 3;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        // Render each line
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x-10, y);
            y += 40; // Line spacing
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255, 200);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

}
