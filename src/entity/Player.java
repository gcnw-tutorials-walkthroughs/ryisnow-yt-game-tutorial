package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//REVIEWED 13APR2022
//
//  setDefaultValues()  changeSpriteNum()
//  getPlayerImage()    draw()
//  update()
public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
//    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player (GamePanel gp, KeyHandler keyH){
        super(gp);

        //CONSTRUCT -   player
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();

        //IDENTIFY  -   viewport
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //DEFINE    -   collision region for player element
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 16;
        solidArea.width = 16;
    }

    public void setDefaultValues(){
        //PLAYER START LOCATION
        worldX=gp.tileSize * 23;
        worldY=gp.tileSize * 21;
        direction="down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        speed=4;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));

    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

//RETRIEVE  -   player images from resource folder
    public void getPlayerImage() {
            up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);

        }
    }

    public void update() {

        if (attacking) {
            attacking();
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
                this.changeSpriteNum();
            } else if (keyH.downPressed) {
                direction = "down";
                this.changeSpriteNum();
            } else if (keyH.leftPressed) {
                direction = "left";
                this.changeSpriteNum();
            } else if (keyH.rightPressed) {
                direction = "right";
                this.changeSpriteNum();
            }
//   COLLISION DETECTION   //   COLLISION DETECTION   //   COLLISION DETECTION   //
            collisionOn = false;
//TILE
            gp.cChecker.checkTile(this);
//NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
//OBJECT
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
//MONSTER
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.eHandler.checkEvent();


            if (!collisionOn && !keyH.enterPressed) {
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
            }

            if(keyH.enterPressed && !attackCanceled){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            spriteCounter++;
        }

        if(gp.keyH.shotKeyPressed && projectile.alive == false
                && shotAvailableCounter == 30 && projectile.haveResource(this)){

            //SET DEFAULT VALUES FOR PROJECTILE
            projectile.set(worldX, worldY, direction, true, this);

            projectile.subtractResource(this);
            //ADD IT TO PROJECTILE LIST
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;

            gp.playSE(10);
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }

//METHOD    -   for alternating image for appearance of movement
    public void changeSpriteNum(){
        if(spriteCounter > 25) {
            if(spriteNum == 1) {
                spriteNum = 2;}
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }}

    public void attacking() {
        System.out.println("attacking");
        spriteCounter++;
        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //CURRENT WORLD POSTION
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //ADJUST PLAYER WORLD X/Y FOR ATTACK AREA
            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //CHECK FOR COLLISION WITH UPDATED HITBOX LOCATION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

        }

        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int objectIndex){
        if(objectIndex != 999) {
            String text;

            if(inventory.size() <= maxInventorySize){
                inventory.add(gp.obj[objectIndex]);
                gp.playSE(1);
                text = "Got a " + gp.obj[objectIndex].name + "!";
            }
            else {
                text = "You cannot carry anymore!";
            }
            gp.ui.addMessage(text);
            gp.obj[objectIndex] = null;
        }
    }

    public void interactNPC(int npcIndex) {
        if (npcIndex != 999 && gp.keyH.enterPressed) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
        }
    }

    public void contactMonster(int monsterIndex) {
        if (monsterIndex != 999) {
            if (!invincible && !gp.monster[monsterIndex].dying) {
                gp.playSE(6);
                int damage = gp.monster[monsterIndex].attack - defense;
                if(damage < 0) {damage = 0;}
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int monsterIndex, int attack) {
        if(monsterIndex != 999){
            if(gp.monster[monsterIndex].invincible == false) {
                gp.playSE(5);

                int damage = attack - gp.monster[monsterIndex].defense;
                if(damage < 0) {damage = 0;}
                gp.monster[monsterIndex].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[monsterIndex].invincible = true;
                gp.monster[monsterIndex].damageReaction();

                if(gp.monster[monsterIndex].life <= 0){
                    gp.monster[monsterIndex].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[monsterIndex].name + "!");
                    gp.ui.addMessage("EXP + " + gp.monster[monsterIndex].exp + "!");
                    exp += gp.monster[monsterIndex].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void checkLevelUp() {
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp *= 1.25;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are now level " + level + " now!\n" +
                                    "You feel STRONGER!";
        }
}
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
//DRAW - player on screen
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction) {
            case "up":
                if(attacking == false){
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                    break;
                }
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
                    break;
                }
            case "down":
                if(attacking == false){
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                    break;
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
                    break;
                }
            case "left":
                if(attacking == false){
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                    break;
                }
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){image = attackLeft1;}
                    if(spriteNum == 2){image = attackLeft2;}
                    break;
                }
            case "right":
                if(attacking == false){
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                    break;
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackRight1;}
                    if(spriteNum == 2){image = attackRight2;}
                    break;
                }
        }

        int x = screenX;
        int y = screenY;

        if(screenX > worldX){x = worldX;}
        if(screenY > worldY){y = worldY;}
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX){x = gp.screenWidth - (gp.worldWidth - worldX);}
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY){y = gp.screenHeight - (gp.worldHeight - worldY);}

        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//        g2.setFont(new Font("Arial",Font.PLAIN,26));
//        g2.setColor(Color.WHITE);
//        g2.drawString("Invincible:" + invincibleCounter, 10, 400);

    }
}

//ImageIO:  A class containing static convenience methods for locating ImageReaders and ImageWriters, and
//          performing simple encoding and decoding.
//getClass(): https://stackabuse.com/javas-object-methods-getclass/
//getResourceAsStream(): Returns an input stream for reading the specified resource.

