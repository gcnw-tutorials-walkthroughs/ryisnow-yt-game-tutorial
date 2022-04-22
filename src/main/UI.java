package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font saturno, purisaB;
    BufferedImage heart_full,heart_half,heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: second screen, ...


    public UI(GamePanel gp){
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/saturno.ttf");
            saturno = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//CREATE LIFE PANEL FOR HUD
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(purisaB);
        g2.setColor(Color.WHITE);

//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE//SET GAME STATE//
//TITLE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
//PLAY
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
//PAUSE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
//DIALOGUE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

//DRAW BLANK HEARTS FOR MAX LIFE
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while(i < gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
            }
        }



    public void drawTitleScreen(){
        if(titleScreenState == 0) {
            g2.setColor(new Color(13, 54, 200));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64));
            String text = "Blue Boy Adventure";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 5 / 2;
            //SHADOW TEXT
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, y + 5);
            //MAIN TEXT
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            //BLUE BOY IMAGE
            x = gp.screenWidth / 2 - gp.tileSize * 3 / 2;
            y += gp.tileSize;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 5 / 2, gp.tileSize * 5 / 2, null);
            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "LOAD GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize * 3 / 2;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text);
            y += gp.tileSize * 3 / 2;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

        else if(titleScreenState == 1){
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXForCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text,x,y);

            text = "Fighter";
            x = getXForCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text,x,y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Thief";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Back";
            x = getXForCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
    }

    public void drawPauseScreen(){
        g2.setFont(saturno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 65));
        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){
        int x = gp.tileSize;
        int y = gp.tileSize/2;
        int width = gp.screenWidth-(gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }
    }

    public void drawSubWindow(int x,int y,int width,int height){

        Color c = new Color(0,0,0, 175);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, gp.tileSize/2,gp.tileSize/2);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, gp.tileSize/2-10,gp.tileSize/2-10);
    }

    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
