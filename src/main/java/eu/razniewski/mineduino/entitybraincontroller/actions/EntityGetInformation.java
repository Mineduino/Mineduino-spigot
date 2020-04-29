package eu.razniewski.mineduino.entitybraincontroller.actions;

import com.github.ysl3000.bukkit.pathfinding.goals.PathfinderGoalMoveToLocation;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityContext;
import eu.razniewski.mineduino.entitybraincontroller.data.EntityInformationData;
import eu.razniewski.mineduino.worldtominecraft.evaluators.EntityEvaluator;
import org.bukkit.Location;

import java.util.Objects;

public class EntityGetInformation extends EntityRequest {
    public static final String JSONTYPE = "getInfo";

    @Override
    public void processBrain(BrainController controller, EntityContext context) {
        String topic = context.getContextTopic() + "Ret";
        EntityInformationData data = EntityInformationData.from(controller.getEntity());
        MineduinoPlugin.getMqttHandler().standardPublish(topic, EntityEvaluator.getGsonEvaluator().toJson(data));

    }
}
