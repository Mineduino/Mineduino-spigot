package com.mineduino.MineduinoSpigotPlugin.Listeners;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener{
	
	@EventHandler
	public void blockPlaceListener(BlockPlaceEvent e) {
		if(e.isCancelled()) {return;}
                if(e.getItemInHand().getItemMeta().getDisplayName().startsWith("MD;I;")) {
                    String topic = e.getItemInHand().getItemMeta().getDisplayName().split(";")[2];
                    System.out.println(topic);
                    MineduinoSpigotPlugin.getInputStorager().add(e.getBlock(), topic);
                }
	}
	
}