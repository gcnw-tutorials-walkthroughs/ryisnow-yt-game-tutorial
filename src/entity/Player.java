package entity;

import main.GamePanel;
import main.KeyHandler;

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

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player (GamePanel gp, KeyHandler keyH){
//CONSTRUCT - player
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
//IDENTIFY - viewport
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

//DEFINE - collision region for player element
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
//RETRIEVE - player images from resource folder
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ){
        if (keyH.upPressed){direction = "up"; this.changeSpriteNum();}
        else if (keyH.downPressed){direction="down"; this.changeSpriteNum();}
        else if (keyH.leftPressed){direction="left"; this.changeSpriteNum();}
        else if (keyH.rightPressed){direction="right"; this.changeSpriteNum();}
//IDENTIFY - collisions
        collisionOn = false;
        gp.cChecker.checkTile(this  );
//DETERMINE - reaction to collision
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
    }}

//METHOD - for alternating image for appearance of movement
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
        if(objectIndex != 999) {
            String objectName = gp.obj[objectIndex].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[objectIndex] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSE(3);
                        gp.obj[objectIndex] = null;
                        hasKey--;
                        gp.ui.showMessage("DOOR UNLOCKED!");
                    }
                    else{
                        gp.ui.showMessage("You need a KEY!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[objectIndex] = null;
                    gp.ui.showMessage("Boots Acquired! [SPEED UP]");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    gp.ui.showMessage("CHEST OPENED!");
                    break;
            }
        }
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}

//ImageIO:  A class containing static convenience methods for locating ImageReaders and ImageWriters, and
//          performing simple encoding and decoding.
//getClass(): https://stackabuse.com/javas-object-methods-getclass/
//getResourceAsStream(): Returns an input stream for reading the specified resource.

