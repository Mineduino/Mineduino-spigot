package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.util.UUID;

import org.bukkit.block.Block;

class TBlock {
	
	protected String ID;
	protected Block block;
	protected UUID owner;
	
	TBlock(String ID, Block block, UUID owner){
		this.ID = ID;
		this.block = block;
		this.owner = owner;
	}
	
}
