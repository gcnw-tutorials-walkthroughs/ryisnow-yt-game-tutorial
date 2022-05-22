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
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;


    //COUNTERS
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

// ENTITY ATTRIBUTES | ENTITY ATTRIBUTES | ENTITY ATTRIBUTES | ENTITY ATTRIBUTES //
    public String name;
    public int maxLife;
    public int maxMana;
    public int mana;
    public int ammo;
    public int life;
    public int speed;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;

    //TYPE
    public int type;                //0[Player],1[NPC],2[Monster]
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;



    public Entity(GamePanel gp){this.gp = gp;}

    public void setAction(){}
    public void damageReaction() {}
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

    public void use(Entity entity){}

    public void checkDrop(){}
    public void dropItem(Entity droppedItem){
        for(int i = 0;i < gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;  //ITEM APPEARS AT
                gp.obj[i].worldY = worldY;  //DEAD MONSTER LOCATION
                break;
            }
        }
    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        gp.cChecker.checkPlayer(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
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
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

    }
    public void changeSpriteNum(){
//METHOD    -   for alternating image for appearance of movement
        if(spriteCounter > 14) {
            if(spriteNum == 1) {
                spriteNum = 2;}
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }}
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;

        int dyingSpeed = 5;

        if(dyingCounter <= dyingSpeed) {changeAlpha(g2,0f);}
        if(dyingCounter > dyingSpeed && dyingCounter <= dyingSpeed*2) {changeAlpha(g2,1f);}
        if(dyingCounter > dyingSpeed*2 && dyingCounter <= dyingSpeed*3) {changeAlpha(g2,0f);}
        if(dyingCounter > dyingSpeed*3 && dyingCounter <= dyingSpeed*4) {changeAlpha(g2,1f);}
        if(dyingCounter > dyingSpeed*4 && dyingCounter <= dyingSpeed*5) {changeAlpha(g2,0f);}
        if(dyingCounter > dyingSpeed*5 && dyingCounter <= dyingSpeed*6) {changeAlpha(g2,1f);}
        if(dyingCounter > dyingSpeed*6 && dyingCounter <= dyingSpeed*7) {changeAlpha(g2,0f);}
        if(dyingCounter > dyingSpeed*7 && dyingCounter <= dyingSpeed*8) {changeAlpha(g2,1f);}
        if(dyingCounter > dyingSpeed*8) {
            alive = false;
        }

    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }
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
    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            gp.playSE(6);
            int damage = attack - gp.player.defense;
            if(damage < 0) {damage = 0;}
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
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

            //MONSTER HEALTH BAR
            if(type == 2 && hpBarOn){

                double oneScale = (double) gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2, 12);                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15, (int) hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 180){
                    hpBarCounter=0;
                    hpBarOn = false;
                }
            }


            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4f);
            }

            if(dying) {dyingAnimation(g2);}
            g2.drawImage(image, screenX ,screenY,null);}

            changeAlpha(g2,1f);

    }
}

//BufferedImage -   describes an image with an accessible buffer of data
//                  (stores our images)
//              -   comprised of a `ColorModel` and `Raster` of image data