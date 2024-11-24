package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 pixels
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol; //1440 pixels
    public final int worldHeight = tileSize * maxWorldRow; //1440 pixels

    //FPS
    int FPS = 60;

    //SYSTEM
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound soundEffect = new Sound();
    Sound music = new Sound();
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    //ENTITY AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] mob = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public boolean debug;
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setUpGame(){
        assetSetter.setObjects();
        assetSetter.setNPCs();
        assetSetter.setMOBs();
        //playMusic(0);
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            //Game Loop - Update, Render, Sleep
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (int) (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        if(gameState == playState){
            player.update();
            //OBJECTS UPDATE
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (Entity entity : mob) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
        if(gameState == pauseState){
            //Pause Menu
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //DEBUGGING
        long drawStartTime = 0;
        if(keyHandler.drawTime){
            drawStartTime = System.nanoTime();
        }
        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else {
            //TILE RENDERING
            tileManager.renderMap(g2);
            //OBJECT RENDERING
            entityList.add(player);

            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : mob) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            //SORT ENTITY LIST
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });
            //RENDER ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            //RESET ENTITY LIST
            entityList.clear();

            //UI RENDERING
            ui.draw(g2);

            //DEBUGGING
            if(keyHandler.drawTime){
                long drawEndTime = System.nanoTime();
                long drawTime = drawEndTime - drawStartTime;
                g2.setColor(Color.WHITE);
                g2.drawString("Draw Time: " + drawTime, 10, 400);
                System.out.println("Draw Time: " + drawTime);
            }
            g2.dispose();
        }
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }
    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
