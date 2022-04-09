package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player (GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();
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
            //ImageIO:  A class containing static convenience methods for locating ImageReaders and ImageWriters, and
            //          performing simple encoding and decoding.
            //getClass(): https://stackabuse.com/javas-object-methods-getclass/
            //getResourceAsStream(): Returns an input stream for reading the specified resource.
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
        if (keyH.upPressed == true){
            direction = "up";
            worldY -= speed;
            this.changeSpriteNum();}
        else if (keyH.downPressed == true){
            direction="down";
            worldY += speed;
            this.changeSpriteNum();}
        else if (keyH.leftPressed == true){
            direction="left";
            worldX -= speed;
            this.changeSpriteNum();}
        else if (keyH.rightPressed == true){
            direction="right";
            worldX += speed;
            this.changeSpriteNum();}

        spriteCounter++;
    }

    public void changeSpriteNum(){
        if(spriteCounter > 14) {
            if(spriteNum == 1) {
                spriteNum = 2;}
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(x,y, gp.tileSize, gp.tileSize);

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
