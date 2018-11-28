package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.util.UUID;

import org.bukkit.block.Block;

public interface TriggerBlock {
	
	public String getID();
	public Block getBlock();
	public UUID getOwner();
	
}