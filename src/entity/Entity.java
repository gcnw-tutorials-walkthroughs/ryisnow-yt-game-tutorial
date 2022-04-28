package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//
//REVIEWED 13APR2022
//
public class Entity {

    GamePanel gp;
    //DECLARE VARIABLES
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2;
    public BufferedImage attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image,image2,image3;
    public int worldX, worldY;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    String dialogues[] = new String[20];


    //STATE TRACKING
    public boolean invincible = false;
    public Boolean collisionOn = false;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collision = false;
    boolean attacking = false;


    //COUNTERS
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;

    //ENTITY STATUS
    public String name;
    public int maxLife;
    public int life;
    public int speed;
    public int type;                //0[Player],1[NPC],2[Monster]


    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true){
            if(!gp.player.invincible){
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
            spriteCounter++;
            this.changeSpriteNum();

            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
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


        public BufferedImage setup(String imagePath, int width, int height){
            UtilityTool uTool = new UtilityTool();
            BufferedImage image = null;
            try{
                image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
                image = uTool.scaleImage(image, width, height);
            }catch(IOException e){
                e.printStackTrace();
            }
            return image;
        }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if( worldX > (gp.player.worldX - gp.player.screenX - gp.tileSize) &&
                worldX < (gp.player.worldX + gp.player.screenX + gp.tileSize) &&
                worldY > (gp.player.worldY - gp.player.screenY - gp.tileSize) &&
                worldY < (gp.player.worldY + gp.player.screenY + gp.tileSize)){
            switch(direction) {
                case "up":
                    if(spriteNum == 1){image = up1;}
                    else if(spriteNum == 2){image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1){image = down1;}
                    else if(spriteNum == 2){image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1){image = left1;}
                    else if(spriteNum == 2){image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1){image = right1;}
                    else if(spriteNum == 2){image = right2;}
                    break;
            }
            if(invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            g2.drawImage(image, screenX ,screenY, gp.tileSize, gp.tileSize, null);}

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}

//BufferedImage -   describes an image with an accessible buffer of data
//                  (stores our images)
//              -   comprised of a `ColorModel` and `Raster` of image data