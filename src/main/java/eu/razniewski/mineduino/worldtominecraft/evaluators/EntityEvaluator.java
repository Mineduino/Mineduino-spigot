package eu.razniewski.mineduino.worldtominecraft.evaluators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityRequest;
import eu.razniewski.mineduino.utils.ParsedTopic;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
        System.out.println(mineduinoMessageEvent.getTopic());
        if(!parsed.isPresent()) {
            System.out.println("ups");
            return;
        }

        Set<BrainController> controller = MineduinoPlugin.getInstance().getBrainManager().getBrainsFor(parsed.get().getTopic());
        System.out.println("ctrl: " + controller);

        if(controller != null) {
            EntityRequest req = fromByteArray(mineduinoMessageEvent.getMessage());
            System.out.println(req);
            if(req != null) {
                System.out.println("n");
                controller.forEach((brainController -> {
                    brainController.moveTo(req.getDeltaX(), req.getDeltaY(), req.getDeltaZ(), 1);
                }));
            }else {
                MineduinoPlugin.getInstance().getLogger().warning("[PROBLEM] CANT PARSE");

            }
        } else {
            MineduinoPlugin.getInstance().getLogger().warning("[SIMPLE] No location for: " + parsed.get().getIdentifier());
        }

    }
}
