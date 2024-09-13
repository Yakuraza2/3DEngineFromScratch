package src.fr.rhumain.worldcraft;

import lombok.Getter;

import java.awt.Color;

@Getter
public class BlockFace
{
    private Color color;
    private Vector normal;


    
    public BlockFace(Color color, int x, int y, int z){
        this.color = color;
        this.normal = new Vector(x, y, z);
    }

}
