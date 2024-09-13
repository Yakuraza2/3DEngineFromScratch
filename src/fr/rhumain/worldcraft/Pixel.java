package src.fr.rhumain.worldcraft;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

import static src.fr.rhumain.worldcraft.GraphicMotor.SHOW_DISTANCE;

@Getter
@Setter
public class Pixel {
    Color color = Color.BLACK;
    double distance = SHOW_DISTANCE;

    public void tryToSet(double distance, Color color) {
        if(this.distance >= distance){
            this.color = color;
            this.distance = distance;
        }
    }

    public void reset() {
        this.distance = SHOW_DISTANCE;
        this.color = Color.BLACK;
    }
}
