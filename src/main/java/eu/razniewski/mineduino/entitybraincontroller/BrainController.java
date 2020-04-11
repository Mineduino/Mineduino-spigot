package eu.razniewski.mineduino.entitybraincontroller;

import com.github.ysl3000.bukkit.pathfinding.entity.Insentient;
import com.github.ysl3000.bukkit.pathfinding.goals.PathfinderGoalMoveToLocation;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import org.bukkit.Location;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrainController that = (BrainController) o;
        return Objects.equals(entity.getBukkitEntity().getEntityId(), that.entity.getBukkitEntity().getEntityId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity.getBukkitEntity().getEntityId());
    }
}
