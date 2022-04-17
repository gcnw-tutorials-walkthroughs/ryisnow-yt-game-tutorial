package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
//
//REVIEWED 13APR2022
//
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
//PLACE - assets into gameworld
    public void setObject(){
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}
