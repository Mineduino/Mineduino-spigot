package eu.razniewski.mineduino.entitybraincontroller.actions;

import com.github.ysl3000.bukkit.pathfinding.goals.PathfinderGoalMoveToLocation;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityContext;
import org.bukkit.Location;

import java.util.Objects;

public class EntityMoveToPos extends EntityRequest {
    public static final String JSONTYPE = "moveToPos";

    private double speed = 1;
    private double distance = 0.3;
    private double x;
    private double y;
    private double z;
    private boolean pathFinder = false;

    @Override
    public void processBrain(BrainController controller, EntityContext context) {
        Location goTo = new Location(controller.getEntity().getBukkitEntity().getWorld(), x, y, z);
        if(pathFinder) {
            controller.getEntity().addPathfinderGoal(0, new PathfinderGoalMoveToLocation(controller.getEntity(), goTo, speed, distance));
        } else {
            controller.getEntity().getNavigation().moveTo(goTo, speed);
        }


    }
}
