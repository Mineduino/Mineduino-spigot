package eu.razniewski.mineduino.worldtominecraft;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Optional;

public class WorldToMinecraftListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMqttMessage(MineduinoMessageEvent event) {
        Optional<ParsedTopic> parsed = ParsedTopic.from(event.getTopic());
        if(!parsed.isPresent()) {
            return;
        }
        EvaluatorManager.getEvaluatorFor(parsed.get().getType()).accept(event);
    }
}
