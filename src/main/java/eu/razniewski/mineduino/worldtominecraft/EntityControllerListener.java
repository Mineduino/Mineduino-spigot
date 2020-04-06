package eu.razniewski.mineduino.worldtominecraft;

import com.github.ysl3000.bukkit.pathfinding.PathfinderGoalAPI;
import com.github.ysl3000.bukkit.pathfinding.entity.Insentient;
import com.github.ysl3000.bukkit.pathfinding.pathfinding.PathfinderManager;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.lang.reflect.Field;
import java.util.*;

public class EntityControllerListener implements Listener {
    private PathfinderManager manager = PathfinderGoalAPI.getAPI();
    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
//        Field entityF = null;
//        for (Field f :
//                getFields(event.getRightClicked())) {
//            if(f.getType().getCanonicalName().equals("net.minecraft.server.v1_14_R1.Entity")) {
//                entityF = f;
//                break;
//            }
//        }
//        try {
//            entityF.setAccessible(true);
//            Entity realEntity = (Entity) entityF.get(event.getRightClicked());
//            if(realEntity instanceof EntityInsentient) {
//                EntityInsentient insentient = (EntityInsentient) realEntity;
//                PathfinderGoalSelector selector = insentient.goalSelector;
//                PathfinderGoalSelector targetselector = insentient.targetSelector;
//                clearSelector(selector);
//                clearSelector(targetselector);
//
////                selector.a(0, new PathfinderGoalWalkToTile(insentient, 1, 1, 0, 1));
//                Tester.brainControllerMap.put("MD/kaczka/entity", new BrainController(insentient, selector));
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        if(event.getRightClicked() instanceof LivingEntity) {
//            LivingEntity entity = (LivingEntity) event.getRightClicked();
//
//        }
//        PathfinderManager manager = PathfinderGoalAPI.getAPI();
        if(event.getRightClicked() instanceof Creature) {
            Insentient pathFinderGoalEntity = manager.getPathfinderGoalEntity((Creature) event.getRightClicked());
            pathFinderGoalEntity.clearPathfinderGoals();
            pathFinderGoalEntity.clearTargetPathfinderGoals();
            String topic = "MD/kaczka/entity";
            if(Tester.brainControllerMap.containsKey(topic)) {
                Tester.brainControllerMap.get(topic).add(new BrainController(pathFinderGoalEntity));
            } else {
                HashSet<BrainController> newController = new HashSet<>();
                newController.add(new BrainController(pathFinderGoalEntity));
                Tester.brainControllerMap.put(topic, newController);

            }

        }

    }
    private <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private void clearSelector(PathfinderGoalSelector selector) {
        try {
            Field b = selector.getClass().getDeclaredField("d");
            b.setAccessible(true);
            Set<PathfinderGoalWrapped> sels = (Set<PathfinderGoalWrapped>) b.get(selector);
            sels.clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
