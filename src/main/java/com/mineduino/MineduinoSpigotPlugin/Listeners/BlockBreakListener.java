package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener{
	
	@EventHandler
	public void blockBreakListener(BlockBreakEvent e) {
		if(e.isCancelled()) {return;}
	}
	
}
