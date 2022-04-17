package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
//REVIEWED 13APR2022
//
// CHEST OBJECT
public class OBJ_Chest extends SuperObject {

    public OBJ_Chest(GamePanel gp) {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }}}
