package object;

import javax.imageio.ImageIO;
import java.io.IOException;
//REVIEWED 13APR2022
//
// KEY OBJECT
public class OBJ_Key extends SuperObject{

    public OBJ_Key() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }}}
