package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
//REVIEWED 13APR2022
//
// CHEST OBJECT
public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
    }}
