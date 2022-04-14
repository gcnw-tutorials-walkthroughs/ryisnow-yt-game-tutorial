package object;


import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
//REVIEWED 13APR2022
//
//PARENT CLASS OF ALL OBJECTS
public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if( worldX > (gp.player.worldX - gp.player.screenX - gp.tileSize) &&
                worldX < (gp.player.worldX + gp.player.screenX + gp.tileSize) &&
                worldY > (gp.player.worldY - gp.player.screenY - gp.tileSize) &&
                worldY < (gp.player.worldY + gp.player.screenY + gp.tileSize)){
            g2.drawImage(image, screenX ,screenY, gp.tileSize, gp.tileSize, null);}

    }
}
