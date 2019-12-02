package eu.razniewski.mineduino.minecrafttoworld;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.locator.Locator;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

public class OutputerBlockBreakListener implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        Locator loc = MineduinoPlugin.getInstance().getLocator();
        Optional<String> key = loc.getKeyIfValueExists(e.getBlock().getLocation());
        if(key.isPresent()) {
            loc.delete(key.get());
            e.getPlayer().sendMessage("[MD] Output block deleted!");

        }
    }
}
