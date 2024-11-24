package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        tile = new Tile[25];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/resources/maps/FirstMap.txt");
    }
    public void setupImage(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool(gamePanel);

        tile[index] = new Tile();
        tile[index].image = uTool.loadScaledImage(imagePath);
        tile[index].collision = collision;
    }

    public void getTileImage(){
            setupImage(0, "tiles/tileset_00", false);
            setupImage(0, "tiles/tileset_00", false);
            setupImage(1, "tiles/tileset_01", false);
            setupImage(2, "tiles/tileset_02", false);
            setupImage(3, "tiles/tileset_03", false);
            setupImage(4, "tiles/tileset_04", true);
            setupImage(5, "tiles/tileset_05", true);
            setupImage(6, "tiles/tileset_06", true);
            setupImage(7, "tiles/tileset_07", true);
            setupImage(8, "tiles/tileset_08", true);
            setupImage(9, "tiles/tileset_09", true);
            setupImage(10, "tiles/tileset_10", true);
            setupImage(11, "tiles/tileset_11", true);
            setupImage(12, "tiles/tileset_12", true);
            setupImage(13, "tiles/tileset_13", true);
            setupImage(14, "tiles/tileset_14", true);
            setupImage(15, "tiles/tileset_15", true);
            setupImage(16, "tiles/tileset_16", true);
            setupImage(17, "tiles/tileset_17", true);
            setupImage(18, "tiles/tileset_18", true);
            setupImage(19, "tiles/tileset_19", true);
            setupImage(20, "tiles/tileset_20", false);
            setupImage(21, "tiles/tileset_21", false);
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("layer")) {
                    break; // Skip metadata lines
                }
            }

            int row = 0;
            while (row < gamePanel.maxWorldRow && (line = br.readLine()) != null) {
                String[] numbers = line.split(",");
                for (int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderMap(Graphics2D g2){

        UtilityTool uTool = new UtilityTool(gamePanel);

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            //Stop moving the camera at the edge of the map
            if(gamePanel.player.screenX > gamePanel.player.worldX){
                screenX = worldX;
            }
            if(gamePanel.player.screenY > gamePanel.player.worldY){
                screenY = worldY;
            }
            int rightOffset = gamePanel.screenWidth - gamePanel.player.screenX;
            if(rightOffset > gamePanel.worldWidth - gamePanel.player.worldX){
                screenX = gamePanel.screenWidth - (gamePanel.worldWidth - worldX);
            }
            int bottomOffset = gamePanel.screenHeight - gamePanel.player.screenY;
            if(bottomOffset > gamePanel.worldHeight - gamePanel.player.worldY){
                screenY = gamePanel.screenHeight - (gamePanel.worldHeight - worldY);
            }

            if(uTool.isWithinScreenBounds(worldX, worldY, gamePanel)){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }else if(gamePanel.player.screenX > gamePanel.player.worldX || gamePanel.player.screenY > gamePanel.player.worldY ||
                    rightOffset > gamePanel.worldWidth - gamePanel.player.worldX || bottomOffset > gamePanel.worldHeight - gamePanel.player.worldY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gamePanel.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
