package src.fr.rhumain.worldcraft;

import lombok.Getter;

/**
 * Décrivez votre classe src.fr.rhumain.worldcraft.Player ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
@Getter
public class Player extends Entity
{
    public Player(double x, double y, double z){
        super(x, y ,z);
    }

}
