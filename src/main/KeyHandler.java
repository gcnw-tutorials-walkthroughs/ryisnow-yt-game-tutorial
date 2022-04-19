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
    public boolean upPressed, downPressed, leftPressed, rightPressed , enterPressed;
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
//PLAY
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG
        if (code == KeyEvent.VK_T) {
            if (checkDrawTime) {
                checkDrawTime = false;
            } else {
                checkDrawTime = true;
            }
        }
//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG//DEBEG//DEBUG

//PAUSE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
//DIALOGUE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
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
