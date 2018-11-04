package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mineduino.MineduinoSpigotPlugin.Objects.OutputTriggerBlock;

public class SignalEmitEvent extends Event implements Cancellable{
	
	private boolean cancelled = false;
	private OutputTriggerBlock output;
	private int power;
	private Block firstWire;
	
	public SignalEmitEvent(OutputTriggerBlock output, int power, Block firstWire) {
		this.output = output;
		this.power = power;
		this.firstWire = firstWire;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public OutputTriggerBlock getOutputBlock() {
		return this.output;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public Block getFirstWire() {
		return this.firstWire;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	
}
