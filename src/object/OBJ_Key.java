package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
//REVIEWED 13APR2022
//
// KEY OBJECT
public class OBJ_Key extends SuperObject{

    public OBJ_Key(GamePanel gp) {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }}}
