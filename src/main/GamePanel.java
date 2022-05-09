package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//REVIEWED 13APR2022
//
//  setupGame()     startGameThread()
//  update()        paintComponent()
//  run()
public class GamePanel extends JPanel implements Runnable {

//VARIABLE & OBJECT CREATION//VARIABLE & OBJECT CREATION//VARIABLE & OBJECT CREATION
    final int originalTileSize = 16;    //16x16 tile
    final int scale = 3;                //makes pixels look larger

    public int tileSize = originalTileSize * scale;
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

//world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

//FPS
    int FPS = 60;

//Game System/Management
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread; //JAVA NOTE: Threads are startable and stoppable elements

//Entity and Objects
    public Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();

//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;


//GAMEPANEL()
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
        aSetter.setMonster();
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
            for(int i = 0;i < npc.length;i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            for(int i = 0;i < projectileList.size();i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive) {projectileList.get(i).update();}
                    if(!projectileList.get(i).alive) {projectileList.remove(i);}
                }
            }

            for(int i = 0;i < monster.length;i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying) {monster[i].update();}
                    if(!monster[i].alive) {monster[i] = null;}
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
//TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
//TILES
            tileM.draw(g2);

//ADD ENTITIES TO LIST
            entityList.add(player);
            for(int i=0;i< npc.length;i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i=0;i< obj.length;i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i=0;i< monster.length;i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            for(int i=0;i< projectileList.size();i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            for(int i=0;i< entityList.size();i++){
                entityList.get(i).draw(g2);
                }

            entityList.clear();
            }



//UI
            ui.draw(g2);
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
