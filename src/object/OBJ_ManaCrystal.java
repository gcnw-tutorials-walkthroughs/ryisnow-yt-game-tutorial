package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Mana Crystal";
        type = type_pickupOnly;
        value = 1;
        image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        down1 = image;
        }
    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        gp.player.mana += value;
    }
}

