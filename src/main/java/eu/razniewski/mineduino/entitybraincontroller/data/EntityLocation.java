package eu.razniewski.mineduino.entitybraincontroller.data;

import org.bukkit.Location;

import java.io.Serializable;

public class EntityLocation implements Serializable {
    private String world;
    private double x;
    private double y;
    private double z;

    public EntityLocation(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EntityLocation() {
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public static EntityLocation from(Location loc) {
        return new EntityLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());


    }
}
