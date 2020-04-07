package eu.razniewski.mineduino.worldtominecraft;

import com.github.ysl3000.bukkit.pathfinding.PathfinderGoalAPI;
import com.github.ysl3000.bukkit.pathfinding.entity.Insentient;
import com.github.ysl3000.bukkit.pathfinding.pathfinding.PathfinderManager;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;


public class EntityControllerListener implements Listener {
    private PathfinderManager manager = PathfinderGoalAPI.getAPI();
    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Creature) {
            ItemStack stack = event.getPlayer().getInventory().getItemInMainHand();
            if(stack != null && stack.getItemMeta() != null && stack.getType().equals(Material.NAME_TAG)){
                Optional<ParsedTopic> parsed = ParsedTopic.from(stack.getItemMeta().getDisplayName());
                if (!parsed.isPresent()) {
                    return;
                }
                String identifier = parsed.get().getIdentifier();
                String type = parsed.get().getType();
                Insentient pathFinderGoalEntity = manager.getPathfinderGoalEntity((Creature) event.getRightClicked());
                pathFinderGoalEntity.clearPathfinderGoals();
                pathFinderGoalEntity.clearTargetPathfinderGoals();
                MineduinoPlugin.getInstance().getBrainManager().addBrain(parsed.get().getTopic(), new BrainController(pathFinderGoalEntity));
                event.getPlayer().sendMessage("[MD] Entity successfuly controlled! Topic: MD/" + identifier + "/" + type);

            }
        }

    }
}
