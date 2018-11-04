package com.mineduino.MineduinoSpigotPlugin.Objects;

import org.bukkit.block.Block;

public class TriggerBlock {
	
	protected int ID;
	protected final Block block;
	
	public TriggerBlock(int ID, Block block) {
		this.ID = ID;
		this.block = block;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Block getBlock() {
		return this.block;
	}
}