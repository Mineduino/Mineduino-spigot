package com.mineduino.MineduinoSpigotPlugin.Listeners;

import com.mineduino.MineduinoSpigotPlugin.Events.PublishEvent;
import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstoneListener implements Listener{
	
	@EventHandler
	public void blockRedstoneListener(BlockRedstoneEvent e){
            HashSet<Block> done = new HashSet<>();
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        Block checked = e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().getBlockX() + x, e.getBlock().getLocation().getBlockY() + y, e.getBlock().getLocation().getBlockZ() + z);
                        if(!done.contains(checked)) {
                            Optional<List<String>> topics = MineduinoSpigotPlugin.getInputStorager().getAllFromTopic(checked);
                            if(topics.isPresent()) {
                                System.out.println(topics.get());
                                if(e.getOldCurrent() == 0 && e.getNewCurrent() > 0) {
                                    for(String topic: topics.get()) {
                                        Bukkit.getServer().getPluginManager().callEvent(new PublishEvent(topic, true));
                                    }
                                } else if(e.getOldCurrent() > 0 && e.getNewCurrent() == 0) {
                                    for(String topic: topics.get()) {
                                        Bukkit.getServer().getPluginManager().callEvent(new PublishEvent(topic, false));
                                    }
                                }
                            }
                            done.add(checked);
                        }
                    }
                }
            }
            
	}
	
}
