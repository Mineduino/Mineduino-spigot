package eu.razniewski.mineduino.minecrafttoworld;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.locator.Locator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OutputerBlockBreakListener implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        Locator loc = MineduinoPlugin.getInstance().getLocator();
        if(loc.removeAll(e.getBlock().getLocation())) {
            e.getPlayer().sendMessage("[MD] Location removed!");
        }
    }
}
