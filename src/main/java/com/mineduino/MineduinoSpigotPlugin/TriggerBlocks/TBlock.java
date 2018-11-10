package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.util.UUID;

import org.bukkit.block.Block;

class TBlock {
	
	protected int ID;
	protected Block block;
	protected UUID owner;
	
	TBlock(int ID, Block block, UUID owner){
		this.ID = ID;
		this.block = block;
		this.owner = owner;
	}
	
}
