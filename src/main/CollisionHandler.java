package main;
import entity.Entity;

public class CollisionHandler {

    GamePanel gamePanel;
    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        // Reset collision flags
        entity.collisionUp = false;
        entity.collisionDown = false;
        entity.collisionLeft = false;
        entity.collisionRight = false;

        // Check up direction
        int nextTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
        if (nextTopRow >= 0) {
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][nextTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][nextTopRow];
            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionUp = true;
            }
        }
        // Check down direction
        int nextBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
        if (nextBottomRow < gamePanel.maxWorldRow) {
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][nextBottomRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][nextBottomRow];
            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionDown = true;
            }
        }
        // Check left direction
        int nextLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
        if (nextLeftCol >= 0) {
            tileNum1 = gamePanel.tileManager.mapTileNum[nextLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[nextLeftCol][entityBottomRow];
            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionLeft = true;
            }
        }
        // Check right direction
        int nextRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
        if (nextRightCol < gamePanel.maxWorldCol) {
            tileNum1 = gamePanel.tileManager.mapTileNum[nextRightCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[nextRightCol][entityBottomRow];
            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }
        // Ensure mutual exclusivity of vertical and horizontal collision flags
        if (entity.collisionUp && entity.collisionDown) {
            entity.collisionDown = false;
        }
        if (entity.collisionLeft && entity.collisionRight) {
            entity.collisionRight = false;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < gamePanel.obj.length; i++){
            if(gamePanel.obj[i] != null){
                //get entity's collision box
                entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
                entity.collisionBox.y = entity.worldY + entity.collisionBox.y;
                //Get the object's collision box
                gamePanel.obj[i].collisionBox.x = gamePanel.obj[i].worldX + gamePanel.obj[i].collisionBox.x;
                gamePanel.obj[i].collisionBox.y = gamePanel.obj[i].worldY + gamePanel.obj[i].collisionBox.y;

                switch (entity.direction){
                    case "up":
                        entity.collisionBox.y -= entity.speed;
                        if(entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)){
                            if(gamePanel.obj[i].collision){
                                entity.collisionUp = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.collisionBox.y += entity.speed;
                        if(entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)){
                            if(gamePanel.obj[i].collision){
                                entity.collisionDown = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.collisionBox.x -= entity.speed;
                        if (entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionLeft = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.collisionBox.x += entity.speed;
                        if (entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionRight = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                //reset entity's collision box
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;
                gamePanel.obj[i].collisionBox.x = gamePanel.obj[i].collisionBoxDefaultX;
                gamePanel.obj[i].collisionBox.y = gamePanel.obj[i].collisionBoxDefaultY;
            }
        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for(int i = 0; i < target.length; i++){
        if(target[i] != null){
            //get entity's collision box
            entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
            entity.collisionBox.y = entity.worldY + entity.collisionBox.y;
            //Get the object's collision box
            target[i].collisionBox.x = target[i].worldX + target[i].collisionBox.x;
            target[i].collisionBox.y = target[i].worldY + target[i].collisionBox.y;

            if (entity.direction.equals("up")) {
                entity.collisionBox.y -= entity.speed;
                if (entity.collisionBox.intersects(target[i].collisionBox)) {
                    if(target[i] != entity) {
                        entity.collisionUp = true;
                        index = i;
                    }
                }
            }
            if (entity.direction.equals("down")) {
                entity.collisionBox.y += entity.speed;
                if (entity.collisionBox.intersects(target[i].collisionBox)) {
                    if(target[i] != entity) {
                        entity.collisionDown = true;
                        index = i;
                    }
                }
            }
            if (entity.direction.equals("left")) {
                entity.collisionBox.x -= entity.speed;
                if (entity.collisionBox.intersects(target[i].collisionBox)) {
                    if(target[i] != entity) {
                        entity.collisionLeft = true;
                        index = i;
                    }
                }
            }
            if (entity.direction.equals("right")) {
                entity.collisionBox.x += entity.speed;
                if (entity.collisionBox.intersects(target[i].collisionBox)) {
                    if (target[i] != entity) {
                        entity.collisionRight = true;
                        index = i;
                    }
                }
            }
            //reset entity's collision box
            entity.collisionBox.x = entity.collisionBoxDefaultX;
            entity.collisionBox.y = entity.collisionBoxDefaultY;
            target[i].collisionBox.x = target[i].collisionBoxDefaultX;
            target[i].collisionBox.y = target[i].collisionBoxDefaultY;
        }
    }
        return index;
    }
    public boolean checkPlayerCollision(Entity entity){

        boolean contactPlayer = false;

        entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
        entity.collisionBox.y = entity.worldY + entity.collisionBox.y;
        //Get the object's collision box
        gamePanel.player.collisionBox.x = gamePanel.player.worldX + gamePanel.player.collisionBox.x;
        gamePanel.player.collisionBox.y = gamePanel.player.worldY + gamePanel.player.collisionBox.y;

        switch (entity.direction){
            case "up":
                entity.collisionBox.y -= entity.speed;
                if(entity.collisionBox.intersects(gamePanel.player.collisionBox)){
                    entity.collisionUp = true;
                    contactPlayer = true;
                }
                break;
            case "down":
                entity.collisionBox.y += entity.speed;
                if(entity.collisionBox.intersects(gamePanel.player.collisionBox)){
                    entity.collisionDown = true;
                    contactPlayer = true;
                }
                break;
            case "left":
                entity.collisionBox.x -= entity.speed;
                if (entity.collisionBox.intersects(gamePanel.player.collisionBox)) {
                    entity.collisionLeft = true;
                    contactPlayer = true;
                }
                break;
            case "right":
                entity.collisionBox.x += entity.speed;
                if (entity.collisionBox.intersects(gamePanel.player.collisionBox)) {
                    entity.collisionRight = true;
                    contactPlayer = true;
                }
                break;
        }
        //reset entity's collision box
        entity.collisionBox.x = entity.collisionBoxDefaultX;
        entity.collisionBox.y = entity.collisionBoxDefaultY;
        gamePanel.player.collisionBox.x = gamePanel.player.collisionBoxDefaultX;
        gamePanel.player.collisionBox.y = gamePanel.player.collisionBoxDefaultY;

        return contactPlayer;
    }
}
