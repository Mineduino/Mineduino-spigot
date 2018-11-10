package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.util.UUID;

import org.bukkit.block.Block;

public class InputTriggerBlock extends TBlock implements TriggerBlock{
	
	public InputTriggerBlock(int ID, Block block, UUID owner) {
		super(ID, block, owner);
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public UUID getOwner() {
		return this.owner;
	}
	
}