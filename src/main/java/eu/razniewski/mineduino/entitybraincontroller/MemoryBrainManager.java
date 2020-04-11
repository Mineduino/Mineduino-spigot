package eu.razniewski.mineduino.entitybraincontroller;

import com.github.ysl3000.bukkit.pathfinding.entity.Insentient;
import com.github.ysl3000.bukkit.pathfinding.pathfinding.PathfinderManager;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemoryBrainManager implements BrainManager {
    private Map<String, Set<BrainController>> controllers = new HashMap<>();
    @Override
    public Set<BrainController> getBrainsFor(String topic) {
        return controllers.getOrDefault(topic, null);
    }

    @Override
    public void addBrain(String topic, BrainController controller) {
        if(controllers.containsKey(topic)) {
            controllers.get(topic).add(controller);
        } else {
            HashSet<BrainController> newController = new HashSet<>();
            newController.add(controller);
            controllers.put(topic, newController);

        }
    }

}
