package src.fr.rhumain.worldcraft;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Décrivez votre classe src.fr.rhumain.worldcraft.KeyboardListener ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class KeyboardListener implements KeyListener
{
    Player player;
    
    public KeyboardListener(Player player){
        this.player = player;
    }
    
    
    public void keyPressed(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_Z){
            player.addX(0.1);
        }
        if(key.getKeyCode() == KeyEvent.VK_S){
            player.addX(-0.1);
        }
        
        if(key.getKeyCode() == KeyEvent.VK_Q){
            player.addZ(0.1);
        }
        if(key.getKeyCode() == KeyEvent.VK_D){
            player.addZ(-0.1);
        }
        
        if(key.getKeyCode() == KeyEvent.VK_SPACE){
            player.addY(0.1);
        }
        if(key.getKeyCode() == KeyEvent.VK_SHIFT){
            player.addY(-0.1);
        }
        
        if(key.getKeyCode() == KeyEvent.VK_E){
            player.addYaw(0.1);
        }
        if(key.getKeyCode() == KeyEvent.VK_A){
            player.addYaw(-0.1);
        }
        
        if(key.getKeyCode() == KeyEvent.VK_C){
            player.addPitch(0.1);
        }
        if(key.getKeyCode() == KeyEvent.VK_W){
            player.addPitch(-0.1);
        }
    }
    
    public void keyReleased(KeyEvent key) {
        
    }
    
    public void keyTyped(KeyEvent key) {
        
    }
}
