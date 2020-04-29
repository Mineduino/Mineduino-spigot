package eu.razniewski.mineduino.entitybraincontroller;

import com.github.ysl3000.bukkit.pathfinding.pathfinding.PathfinderManager;
import org.bukkit.entity.Entity;

import java.util.Set;

public interface BrainManager {
    public Set<BrainController> getBrainsFor(String topic);
    public void addBrain(String topic, BrainController controller);
}
