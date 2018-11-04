package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener{
	
	@EventHandler
	public void blockPlaceListener(BlockPlaceEvent e) {
		if(e.isCancelled()) {return;}
	}
	
}