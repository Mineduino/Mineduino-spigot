package eu.razniewski.mineduino.entitybraincontroller;

import java.io.Serializable;
import java.util.Objects;

public class EntityRequest implements Serializable {
    private double deltaX;
    private double deltaY;
    private double deltaZ;

    public EntityRequest(double deltaX, double deltaY, double deltaZ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public void setDeltaZ(double deltaZ) {
        this.deltaZ = deltaZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRequest that = (EntityRequest) o;
        return Double.compare(that.deltaX, deltaX) == 0 &&
                Double.compare(that.deltaY, deltaY) == 0 &&
                Double.compare(that.deltaZ, deltaZ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deltaX, deltaY, deltaZ);
    }

    @Override
    public String toString() {
        return "EntityRequest{" +
                "deltaX=" + deltaX +
                ", deltaY=" + deltaY +
                ", deltaZ=" + deltaZ +
                '}';
    }
}
