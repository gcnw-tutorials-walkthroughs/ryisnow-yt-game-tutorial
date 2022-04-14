package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
//
//REVIEWED 13APR2022
//
public class Entity {
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public Boolean collisionOn = false;
}

//BufferedImage -   describes an image with an accessible buffer of data
//                  (stores our images)
//              -   comprised of a `ColorModel` and `Raster` of image data