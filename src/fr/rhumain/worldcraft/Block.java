package src.fr.rhumain.worldcraft;


import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static src.fr.rhumain.worldcraft.Models.BLOCK;

/**
 * Décrivez votre classe src.fr.rhumain.worldcraft.Block ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
@Getter
public class Block
{

    @Setter
    @Getter
    private Location location;
    public final BlockFace NORTH = new BlockFace(Color.BLUE, 1, 0, 0);
    public final BlockFace SOUTH = new BlockFace(Color.GREEN, -1, 0, 0);
    public final BlockFace EAST = new BlockFace(Color.MAGENTA, 0, 0, 1);
    public final BlockFace WEST = new BlockFace(Color.ORANGE, 0, 0, -1);
    public final BlockFace UP = new BlockFace(Color.PINK, 0, 1, 0);
    public final BlockFace DOWN = new BlockFace(Color.RED, 0, -1, 0);

    @Getter
    public final List<BlockFace> faces = new ArrayList<>();
    
    public Block(int x, int y, int z){
        this.location = new Location(x, y ,z, 0, 0);
        this.faces.add(UP);
        this.faces.add(NORTH);
        this.faces.add(SOUTH);
        this.faces.add(EAST);
        this.faces.add(WEST);
        this.faces.add(DOWN);
    }

    public Model getModel(){ return BLOCK.get(); }

}
