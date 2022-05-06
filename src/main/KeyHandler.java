package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//REVIEWED 13APR2022
//
//  keyPressed()
//  keyReleased()
//  keyTyped()
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed , enterPressed, pausePressed;
//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG
    boolean checkDrawTime = false;
//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
//GAME STATE KEY TOGGLES//GAME STATE KEY TOGGLES//GAME STATE KEY TOGGLES//GAME STATE KEY TOGGLES
        if(gp.gameState == gp.titleState) {titleState(code);}
        else if (gp.gameState == gp.playState) {playState(code);}
        else if (gp.gameState == gp.pauseState) {pauseState(code);}
        else if (gp.gameState == gp.dialogueState) {dialogueState(code);}
        else if(gp.gameState == gp.characterState) {characterState(code);}
    }

    public void titleState(int code){
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 2 : gp.ui.commandNum--;}
            if (code == KeyEvent.VK_S) {gp.ui.commandNum = (gp.ui.commandNum++ >= 2) ? 0 : gp.ui.commandNum++;}
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0: gp.ui.titleScreenState = 1; break;
//                  case 1: LOAD STUFF
                    case 2: System.exit(0);}
            }}
        else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 3 : gp.ui.commandNum--;}
            if (code == KeyEvent.VK_S) {gp.ui.commandNum = (gp.ui.commandNum++ >= 3) ? 0 : gp.ui.commandNum++;}
            if (code == KeyEvent.VK_ENTER) {
                gp.playMusic(0);
                switch (gp.ui.commandNum) {
                    //FIGHTER STUFF
                    case 0: gp.gameState = gp.playState; break;
                    //THIEF STUFF
                    case 1: gp.gameState = gp.playState; break;
                    //SORCERER STUFF
                    case 2: gp.gameState = gp.playState; break;
                    //RETURN TO MAIN SCREEN
                    case 3:
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                        break;
                }}}}

    public void playState(int code){
        if (code == KeyEvent.VK_W) {upPressed = true;}
        if (code == KeyEvent.VK_S) {downPressed = true;}
        if (code == KeyEvent.VK_A) {leftPressed = true;}
        if (code == KeyEvent.VK_D) {rightPressed = true;}
        if (code == KeyEvent.VK_P) {gp.gameState = gp.pauseState;}
        if (code == KeyEvent.VK_C) {gp.gameState = gp.characterState;}
        if (code == KeyEvent.VK_ENTER) {enterPressed = true;}
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {gp.gameState = gp.playState;}
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {gp.gameState = gp.playState;}
    }

    public void characterState(int code) {
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W && gp.ui.slotRow > 0){gp.ui.slotRow--; gp.playSE(9);}
        if(code == KeyEvent.VK_A && gp.ui.slotCol > 0){gp.ui.slotCol--; gp.playSE(9);}
        if(code == KeyEvent.VK_S && gp.ui.slotRow < 3){gp.ui.slotRow++; gp.playSE(9);}
        if(code == KeyEvent.VK_D && gp.ui.slotCol < 4){gp.ui.slotCol++; gp.playSE(9);}
        if(code == KeyEvent.VK_ENTER){gp.player.selectItem();}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){upPressed = false;}
        if(code == KeyEvent.VK_S){downPressed = false;}
        if(code == KeyEvent.VK_A){leftPressed = false;}
        if(code == KeyEvent.VK_D){rightPressed = false;}
     }

    //UNNEEDED METHOD
    public void keyTyped(KeyEvent e) {}

}
