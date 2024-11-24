package main;

public class EventHandler {
    GamePanel gamePanel;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void checkEvent(){
        //Check if the player character is more than 1 tile away from the previous event
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        if(xDistance > gamePanel.tileSize || yDistance > gamePanel.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
            if(hit(3, 3,"any"))damagePlayer(3,3,gamePanel.dialogueState);
            if(hit(2,3,"up"))healingPool(2,3,gamePanel.dialogueState);
        }

    }

    public boolean hit(int col, int row, String requiredDirection){
        boolean hit = false;
        gamePanel.player.collisionBox.x = gamePanel.player.worldX + gamePanel.player.collisionBox.x;
        gamePanel.player.collisionBox.y = gamePanel.player.worldY + gamePanel.player.collisionBox.y;
        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        if(gamePanel.player.collisionBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if(gamePanel.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")){
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;

            }
        }
        gamePanel.player.collisionBox.x = gamePanel.player.collisionBoxDefaultX;
        gamePanel.player.collisionBox.y = gamePanel.player.collisionBoxDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void damagePlayer(int col, int row,int gameState){
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fell into a pit!";
        gamePanel.player.health -= 1;
        canTouchEvent = false;
    }
    public void healingPool(int col, int row, int gameState){
        if(gamePanel.keyHandler.ePressed) {
            gamePanel.gameState = gameState;
            gamePanel.ui.currentDialogue = "You found a healing pool!\nYou feel refreshed!";
            gamePanel.player.health = gamePanel.player.maxHealth;
            eventRect[col][row].eventDone = true;
        }
    }
}
