package com.mineduino.MineduinoSpigotPlugin.Events;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SignalEvent extends Event implements Cancellable{
	
	private boolean cancelled = false;
	private Block initBlock;
	private int power;
	private ArrayList<Block> affectedWires;
	
	public SignalEvent(Block initBlock, int power) {
		this.initBlock = initBlock;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public Block getInitBlock() {
		return this.initBlock;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
}
