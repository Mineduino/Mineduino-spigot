package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.OutputTriggerBlock;

public class SignalEmitEvent extends Event implements Cancellable{
	
	private boolean cancelled = false;
	private OutputTriggerBlock output;
	private boolean powered;
	
	private final static HandlerList handlers = new HandlerList();
	
	public SignalEmitEvent(OutputTriggerBlock output, boolean powered) {
		this.output = output;
		this.powered = powered;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
        
        public static HandlerList getHandlerList() {
            return handlers;
        }
	
	public OutputTriggerBlock getOutputBlock() {
		return this.output;
	}
	
	public boolean isPoweredAfterEmit() {
		return this.powered;
	}
	
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
}
