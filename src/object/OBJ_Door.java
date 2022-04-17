package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
//REVIEWED 13APR2022
//
// DOOR OBJECT
public class OBJ_Door extends SuperObject {

    public OBJ_Door(GamePanel gp) {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }}
