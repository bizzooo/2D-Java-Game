package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

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
    KeyHandler keyHandler = new KeyHandler();
    Sound soundEffect = new Sound();
    Sound music = new Sound();
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public ObjectSetter objectSetter = new ObjectSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public SuperObject[] obj = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void setUpGame(){
        objectSetter.setObjects();
        playMusic(0);
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
        player.update();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //DEBUGGING
        long drawStartTime = 0;
        if(keyHandler.drawTime){
            drawStartTime = System.nanoTime();
        }

        //TILE RENDERING
        tileManager.renderMap(g2);
        //OBJECT RENDERING
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }
        //PLAYER RENDERING
        player.render(g2);
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
