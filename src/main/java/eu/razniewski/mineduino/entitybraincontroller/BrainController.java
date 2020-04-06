package eu.razniewski.mineduino.entitybraincontroller;

import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;

public class BrainController {
    private EntityInsentient entity;
    private PathfinderGoalSelector selector;

    public BrainController(EntityInsentient entity, PathfinderGoalSelector selector) {
        this.entity = entity;
        this.selector = selector;
    }

    public void setEntity(EntityInsentient entity) {
        this.entity = entity;
    }

    public void setSelector(PathfinderGoalSelector selector) {
        this.selector = selector;
    }

    public EntityInsentient getEntity() {
        return entity;
    }

    public PathfinderGoalSelector getSelector() {
        return selector;
    }
}
