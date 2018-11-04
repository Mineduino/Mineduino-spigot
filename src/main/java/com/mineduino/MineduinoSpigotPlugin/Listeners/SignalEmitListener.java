package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.mineduino.MineduinoSpigotPlugin.Events.SignalEmitEvent;

public class SignalEmitListener implements Listener{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void signalEmitListener(SignalEmitEvent e) {
		if(!e.isCancelled()) {
			e.getOutputBlock().emitSignal();
		}
	}
}