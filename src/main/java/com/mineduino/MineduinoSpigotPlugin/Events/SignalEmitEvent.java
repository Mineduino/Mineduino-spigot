package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.OutputTriggerBlock;

public class SignalEmitEvent extends Event implements Cancellable{
	
	private boolean cancelled = false;
	private OutputTriggerBlock output;
	private Block firstWireBlock;
	private int oldPower;
	private int power;
	
	private final static HandlerList handlers = new HandlerList();
	
	public SignalEmitEvent(OutputTriggerBlock output, Block firstWireBlock, int oldPower, int power) {
		this.output = output;
		this.firstWireBlock = firstWireBlock;
		this.oldPower = oldPower;
		this.power = power;
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
	
	public Block getFirstWireBlock() {
		return this.firstWireBlock;
	}
	
	public int getOldPower() {
		return this.oldPower;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	
}
