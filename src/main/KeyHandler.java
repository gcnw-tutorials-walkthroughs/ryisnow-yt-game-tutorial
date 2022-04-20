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
//TITLE STATE
        if(gp.gameState == gp.titleState) {
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 2 : gp.ui.commandNum--;
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum = (gp.ui.commandNum++ >= 2) ? 0 : gp.ui.commandNum++;
                }
                if (code == KeyEvent.VK_ENTER) {
                    switch (gp.ui.commandNum) {
                        case 0:
                            gp.ui.titleScreenState = 1;
                            break;
//                    case 1:
//                        //LOAD STUFF
                        case 2:
                            System.exit(0);
                    }
                }
            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum = (gp.ui.commandNum-- <= 0) ? 3 : gp.ui.commandNum--;
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum = (gp.ui.commandNum++ >= 3) ? 0 : gp.ui.commandNum++;
                }
                if (code == KeyEvent.VK_ENTER) {
                    switch (gp.ui.commandNum) {
                        case 0:
                            System.out.println("Fighter specific stuff.");
                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                            break;
                        case 1:
                            System.out.println("Thief specific stuff.");
                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                            break;
                        case 2:
                            System.out.println("Sorcerer specific stuff.");
                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                            break;
                        case 3:
                            gp.ui.titleScreenState = 0;
                            gp.ui.commandNum = 0;
                            break;
                    }
                }
            }
        }

//PLAY

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
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
                System.out.println("Hello");
            }
            else if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
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
//        else if (gp.gameState == gp.pauseState) {
//            if (code == KeyEvent.VK_P) {
//                gp.gameState = gp.playState;
//
//            }
//        }
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
