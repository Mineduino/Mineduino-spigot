package eu.razniewski.mineduino.worldtominecraft.evaluators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.entitybraincontroller.BrainController;
import eu.razniewski.mineduino.entitybraincontroller.EntityContext;
import eu.razniewski.mineduino.entitybraincontroller.actions.EntityGetInformation;
import eu.razniewski.mineduino.entitybraincontroller.actions.EntityMoveToDelta;
import eu.razniewski.mineduino.entitybraincontroller.actions.EntityMoveToPos;
import eu.razniewski.mineduino.entitybraincontroller.actions.EntityRequest;
import eu.razniewski.mineduino.utils.ParsedTopic;
import eu.razniewski.mineduino.utils.RuntimeTypeAdapterFactory;

import javax.swing.text.html.parser.Entity;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class EntityEvaluator implements Consumer<MineduinoMessageEvent> {
    private static RuntimeTypeAdapterFactory<EntityRequest> entityAdapter = RuntimeTypeAdapterFactory.of(EntityRequest.class, "type")
            .registerSubtype(EntityMoveToDelta.class, EntityMoveToDelta.JSONTYPE)
            .registerSubtype(EntityMoveToPos.class, EntityMoveToPos.JSONTYPE)
            .registerSubtype(EntityGetInformation.class, EntityGetInformation.JSONTYPE);
    private static Gson gsonEvaluator = new GsonBuilder().registerTypeAdapterFactory(entityAdapter).create();

    public static Gson getGsonEvaluator() {
        return gsonEvaluator;
    }

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

        Set<BrainController> controller = MineduinoPlugin.getInstance().getBrainManager().getBrainsFor(parsed.get().getTopic());

        if(controller != null) {
            EntityRequest req = fromByteArray(mineduinoMessageEvent.getMessage());
            EntityContext context = new EntityContext(mineduinoMessageEvent.getTopic());
            if(req != null) {
                controller.forEach((brain -> {
                    req.processBrain(brain, context);
                }));
            }else {
                MineduinoPlugin.getInstance().getLogger().warning("[PROBLEM] CANT PARSE");

            }
        } else {
            MineduinoPlugin.getInstance().getLogger().warning("[SIMPLE] No location for: " + parsed.get().getIdentifier());
        }

    }
}
