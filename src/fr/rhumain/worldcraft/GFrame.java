package src.fr.rhumain.worldcraft;

import javax.swing.JFrame;

/**
 * Décrivez votre classe src.fr.rhumain.worldcraft.GFrame ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class GFrame extends JFrame
{
    public GFrame(Player player){
        super();
        this.setName("WorldCraft");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        
        this.addKeyListener(new KeyboardListener(player));

        setFocusable(true);
        requestFocus(); 
    }
}
