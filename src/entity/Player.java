package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
//REVIEWED 13APR2022
//
//  setDefaultValues()  changeSpriteNum()
//  getPlayerImage()    draw()
//  update()
public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player (GamePanel gp, KeyHandler keyH){
        super(gp);
//CONSTRUCT -   player
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
//IDENTIFY  -   viewport
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
//DEFINE    -   collision region for player element
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 32;
        solidArea.width = 32;
    }

    public void setDefaultValues(){
        worldX=gp.tileSize * 23;
        worldY=gp.tileSize * 21;
        speed=4;
        direction="down";
    }
//RETRIEVE  -   player images from resource folder
    public void getPlayerImage() {
            up1 = setup("/player/boy_up_1");
            up2 = setup("/player/boy_up_2");
            down1 = setup("/player/boy_down_1");
            down2 = setup("/player/boy_down_2");
            left1 = setup("/player/boy_left_1");
            left2 = setup("/player/boy_left_2");
            right1 = setup("/player/boy_right_1");
            right2 = setup("/player/boy_right_2");
    }

    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ){
        if (keyH.upPressed){direction = "up"; this.changeSpriteNum();}
        else if (keyH.downPressed){direction="down"; this.changeSpriteNum();}
        else if (keyH.leftPressed){direction="left"; this.changeSpriteNum();}
        else if (keyH.rightPressed){direction="right"; this.changeSpriteNum();}
//DETERMINE -   reaction to collision
        collisionOn = false;
//TILE COLLISION//TILE COLLISION//TILE COLLISION//TILE COLLISION//TILE COLLISION//TILE COL
        gp.cChecker.checkTile(this);
//NPC COLLISION//NPC COLLISION//NPC COLLISION//NPC COLLISION//NPC COLLISION//NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);
//OBJECT COLLISION//OBJECT COLLISION//OBJECT COLLISION//OBJECT COLLISION//OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this,true);
        pickUpObject(objIndex);

        if(collisionOn == false) {
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }}
        spriteCounter++;
    }
}

//METHOD    -   for alternating image for appearance of movement
    public void changeSpriteNum(){
        if(spriteCounter > 14) {
            if(spriteNum == 1) {
                spriteNum = 2;}
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }}

    public void pickUpObject(int objectIndex){
        if(objectIndex != 999) {}
    }

    public void interactNPC(int npcIndex){
        if(npcIndex != 999) {
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
            }
        }
        gp.keyH.enterPressed = false;
    }

//DRAW - player on screen
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;}
                else if(spriteNum == 2){
                    image = up2;
                }
                    break;
            case "down":
                if(spriteNum == 1){
                    image = down1;}
                else if(spriteNum == 2){
                    image = down2;
                }
                    break;
            case "left":
                if(spriteNum == 1){
                    image = left1;}
                else if(spriteNum == 2){
                    image = left2;
                }
                    break;
            case "right":
                if(spriteNum == 1){
                    image = right1;}
                else if(spriteNum == 2){
                    image = right2;
                }
                    break;
        }

        int x = screenX;
        int y = screenY;

        if(screenX > worldX){x = worldX;}
        if(screenY > worldY){y = worldY;}
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX){x = gp.screenWidth - (gp.worldWidth - worldX);}
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY){y = gp.screenHeight - (gp.worldHeight - worldY);}

        g2.drawImage(image, x, y, null);
    }
}

//ImageIO:  A class containing static convenience methods for locating ImageReaders and ImageWriters, and
//          performing simple encoding and decoding.
//getClass(): https://stackabuse.com/javas-object-methods-getclass/
//getResourceAsStream(): Returns an input stream for reading the specified resource.

