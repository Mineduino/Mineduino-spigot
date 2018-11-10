package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import com.mineduino.MineduinoSpigotPlugin.Events.SignalEmitEvent;

public class OutputTriggerBlock extends TBlock implements TriggerBlock{
	
	private int power = 0;
	
	public OutputTriggerBlock(int ID, Block block, UUID owner) {
		super(ID, block, owner);
	}
	
	public int getPower() {
		return this.power;
	}
	
	public Block getFirstWireBlock() {
		return this.block.getRelative(BlockFace.UP);
	}
	
	public void setPower(int power) {
		int oldPower = this.power;
		this.power = power;
		if(this.block.getChunk().isLoaded()) {
			MineduinoSpigotPlugin.instance.getServer().getPluginManager().callEvent(new SignalEmitEvent(this, this.getFirstWireBlock(), oldPower, this.power));
		}
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
