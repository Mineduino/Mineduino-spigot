package eu.razniewski.mineduino.entitybraincontroller;

import com.github.ysl3000.bukkit.pathfinding.entity.Insentient;
import com.github.ysl3000.bukkit.pathfinding.goals.PathfinderGoalMoveToLocation;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import org.bukkit.Location;

public class BrainController {
    private Insentient entity;

    public BrainController(Insentient entity) {
        this.entity = entity;
    }

    public void setEntity(Insentient entity) {
        this.entity = entity;
    }


    public Insentient getEntity() {
        return entity;
    }


    public void moveTo(double deltaX, double deltaY, double deltaZ, double speed) {
        Location gotoloc = this.entity.getBukkitEntity().getLocation().add(deltaX, deltaY, deltaZ);
        this.entity.addPathfinderGoal(0, new PathfinderGoalMoveToLocation(this.entity, gotoloc, speed, 0));

    }

}
