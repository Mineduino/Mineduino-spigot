package eu.razniewski.mineduino.worldtominecraft.evaluators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityRequest;
import eu.razniewski.mineduino.entitybraincontroller.PathfinderGoalWalkToTile;
import eu.razniewski.mineduino.utils.ParsedTopic;
import eu.razniewski.mineduino.worldtominecraft.Tester;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

public class EntityEvaluator implements Consumer<MineduinoMessageEvent> {
    private Gson gsonEvaluator = new GsonBuilder().create();

    EntityRequest fromByteArray(byte[] bytes) {
        try {
            String parsed = new String(bytes, StandardCharsets.UTF_8);
            EntityRequest req = gsonEvaluator.fromJson(parsed, EntityRequest.class);
            return req;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void accept(MineduinoMessageEvent mineduinoMessageEvent) {

        Optional<ParsedTopic> parsed = ParsedTopic.from(mineduinoMessageEvent.getTopic());
        if(!parsed.isPresent()) {
            return;
        }

        BrainController controller = Tester.brainControllerMap.getOrDefault(mineduinoMessageEvent.getTopic(), null);

        if(controller != null) {
            EntityRequest req = fromByteArray(mineduinoMessageEvent.getMessage());
            if(req != null) {
                controller.getSelector().a(0, new PathfinderGoalWalkToTile(controller.getEntity(), 1, req.getDeltaX(), req.getDeltaY(), req.getDeltaZ()));
                System.out.println("OK");
            }else {
                MineduinoPlugin.getInstance().getLogger().warning("[PROBLEM] CANT PARSE");

            }
        } else {
            MineduinoPlugin.getInstance().getLogger().warning("[SIMPLE] No location for: " + parsed.get().getIdentifier());
        }

    }
}
