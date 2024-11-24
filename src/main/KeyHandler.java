package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean ePressed;

    //DEBUG
    public boolean drawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //TITLE STATE
        if(gamePanel.ui.titleScreenState == 0){
            if (gamePanel.gameState == gamePanel.titleState) {
                if(gamePanel.ui.commandNumber !=0 && keyCode == KeyEvent.VK_W){
                    gamePanel.ui.commandNumber--;
                }
                if(gamePanel.ui.commandNumber !=2 && keyCode == KeyEvent.VK_S){
                    gamePanel.ui.commandNumber++;
                }
                if(keyCode == KeyEvent.VK_ENTER){
                    if(gamePanel.ui.commandNumber == 0){
                        gamePanel.ui.titleScreenState = 1;
                    }
                    if(gamePanel.ui.commandNumber == 1){
                        //TODO: Make load game
                    }
                    if(gamePanel.ui.commandNumber == 2){
                        System.exit(0);
                    }
                }
            }
        } else if (gamePanel.gameState == gamePanel.titleState && gamePanel.ui.titleScreenState == 1) {
            if(gamePanel.ui.commandNumber != 0 && keyCode == KeyEvent.VK_W){
                gamePanel.ui.commandNumber--;
            }
            if(gamePanel.ui.commandNumber != 3 && keyCode == KeyEvent.VK_S){
                gamePanel.ui.commandNumber++;
            }
            if(keyCode == KeyEvent.VK_ENTER) {
                if (gamePanel.ui.commandNumber == 0) {
                    System.out.println("Do some warrior stuff");
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 1) {
                    System.out.println("Do some mage stuff");
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 2) {
                    System.out.println("Do some rogue stuff");
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if(gamePanel.ui.commandNumber == 3){
                    gamePanel.ui.commandNumber = 0;
                    gamePanel.ui.titleScreenState = 0;
                }
            }
        }
        //PLAY STATE
        if(gamePanel.gameState == gamePanel.playState) {
            if (keyCode == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (keyCode == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (keyCode == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (keyCode == KeyEvent.VK_D) {
                rightPressed = true;
            }
            //DIALOGUE
            if (keyCode == KeyEvent.VK_E) {
                ePressed = true;
            }
            //PAUSE
            if (keyCode == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.pauseState;
            }
            //DEBUG
            if (keyCode == KeyEvent.VK_T) {
                drawTime = !drawTime;
                gamePanel.debug = !gamePanel.debug;
            }
        }
        //PAUSE STATE
        else if(gamePanel.gameState == gamePanel.pauseState) {
            if (keyCode == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }
        } else if (gamePanel.gameState == gamePanel.dialogueState) {
            if (keyCode == KeyEvent.VK_ENTER) {
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }
        @Override
        public void keyReleased (KeyEvent e){
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_W) {
                upPressed = false;
            }
            if (keyCode == KeyEvent.VK_S) {
                downPressed = false;
            }
            if (keyCode == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (keyCode == KeyEvent.VK_D) {
                rightPressed = false;
            }
            if (keyCode == KeyEvent.VK_E) {
                ePressed = false;
            }
    }
}
