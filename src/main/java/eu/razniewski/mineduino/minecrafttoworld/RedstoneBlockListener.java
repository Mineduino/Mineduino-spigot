package eu.razniewski.mineduino.minecrafttoworld;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.locator.Locator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.Optional;

public class RedstoneBlockListener implements Listener {
    @EventHandler
    public void onRedstoneBlockEvent(BlockRedstoneEvent e) {
        Locator locator = MineduinoPlugin.getInstance().getLocator();
        Optional<String> topic = locator.getKeyIfValueExists(e.getBlock().getLocation());
        if(topic.isPresent()){
            String[] parsed = topic.get().split(";");
            String forTopic = "MD/" + parsed[0] + "/" + parsed[1];
            MineduinoPlugin.getMqttHandler().standardPublish(forTopic, (byte) e.getNewCurrent());
        }
    }
}
