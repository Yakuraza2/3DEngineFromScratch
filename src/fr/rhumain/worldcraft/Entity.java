package src.fr.rhumain.worldcraft;


import lombok.Getter;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Getter
public class Entity {

    private final Location location;
    private final Vector normal;

    public Entity(){
        this(0, 0, 0, 0, 0);
    }

    public Entity(double x, double y, double z){
        this(x, y, z, 0, 0);
    }

    public Entity(double x, double y, double z, double yaw, double pitch){
        this.location = new Location(x, y, z, yaw, pitch);
        this.normal = Vector.fromYawPitch(yaw, pitch);

    }

    public void addX(double a){ this.location.addX(a); }
    public void addZ(double a){ this.location.addZ(a); }
    public void addY(double a){ this.location.addY(a); }
    public void addYaw(double a){
        this.location.addYaw(a);
        this.normal.setYaw(this.location.getYaw());
    }
    public void addPitch(double a){
        this.location.addPitch(a);
        this.normal.setPitch(this.location.getPitch());
    }

}
