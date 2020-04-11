package eu.razniewski.mineduino.entitybraincontroller.actions;

import com.github.ysl3000.bukkit.pathfinding.goals.PathfinderGoalMoveToLocation;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityContext;
import org.bukkit.Location;

import java.util.Objects;

public class EntityMoveToDelta extends EntityRequest {
    public static final String JSONTYPE = "move";

    private double speed = 1;
    private double distance = 0.3;
    private double deltaX = 0.0;
    private double deltaY = 0.0;
    private double deltaZ = 0.0;
    private boolean pathFinder = false;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public boolean isPathFinder() {
        return pathFinder;
    }

    public void setPathFinder(boolean pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMoveToDelta that = (EntityMoveToDelta) o;
        return Double.compare(that.speed, speed) == 0 &&
                Double.compare(that.distance, distance) == 0 &&
                Double.compare(that.deltaX, deltaX) == 0 &&
                Double.compare(that.deltaY, deltaY) == 0 &&
                Double.compare(that.deltaZ, deltaZ) == 0 &&
                pathFinder == that.pathFinder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed, distance, deltaX, deltaY, deltaZ, pathFinder);
    }

    @Override
    public String toString() {
        return "EntityMoveToDelta{" +
                "speed=" + speed +
                ", distance=" + distance +
                ", deltaX=" + deltaX +
                ", deltaY=" + deltaY +
                ", deltaZ=" + deltaZ +
                ", pathFinder=" + pathFinder +
                '}';
    }

    @Override
    public void processBrain(BrainController controller, EntityContext context) {
        if(deltaX == 1 && deltaY == 0 && deltaZ == 0) {
            deltaX = deltaX + 1;
        } else if(deltaY == 1 && deltaX == 0 && deltaZ == 0) {
            controller.getEntity().jump();
            return;
        } else if(deltaZ == 1 && deltaY == 0 && deltaX == 0) {
            deltaZ = deltaZ + 1;
        }

        Location gotoloc = controller.getEntity().getBukkitEntity().getLocation().add(deltaX, deltaY, deltaZ);
        if(pathFinder) {
            controller.getEntity().addPathfinderGoal(0, new PathfinderGoalMoveToLocation(controller.getEntity(), gotoloc, speed, distance));
        } else {
            controller.getEntity().getNavigation().moveTo(gotoloc, speed);
        }


    }
}
