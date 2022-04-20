package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
//REVIEWED 13APR2022
//
//  setupGame()     startGameThread()
//  update()        paintComponent()
//  run()
public class GamePanel extends JPanel implements Runnable {

//DEFINE - viewport settings
    final int originalTileSize = 16;    //16x16 tile
    final int scale = 3;                //makes pixels look larger

    public int tileSize = originalTileSize * scale;
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

//DEFINE - world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

//DEFINE - FPS
    int FPS = 60;

//DEFINE - Game System/Management
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
//JAVA NOTE: Threads are startable and stoppable elements
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
//ENTITY AND OBJECT//ENTITY AND OBJECT//ENTITY AND OBJECT//ENTITY AND OBJECT//ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];
//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


//GAMEPANEL()//GAMEPANEL()//GAMEPANEL()//GAMEPANEL()//GAMEPANEL()//GAMEPANEL()//GAMEPANEL()
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//GAME LOOP
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }}}

    public void update(){
        if(gameState == playState){
            player.update();
            for(int i = 0;i < npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
             }
        }
    }
        if(gameState == pauseState){ui.drawPauseScreen();}
        if(gameState == dialogueState){ui.drawDialogueScreen();}
}

//DRAW COMPONENTS//DRAW COMPONENTS//DRAW COMPONENTS//DRAW COMPONENTS//DRAW COMPONENTS
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
//TILES
            tileM.draw(g2);
//OBJECTS
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2,this);
                }}
//NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }
//PLAYER
            player.draw(g2);

//UI
            ui.draw(g2);

        }

        g2.dispose();
    }

    public void playMusic(int soundIndex){
        music.setFile(soundIndex);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int soundIndex){
        se.setFile(soundIndex);
        se.play();
    }
}
