package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import com.mineduino.MineduinoSpigotPlugin.Events.SignalEmitEvent;

public class OutputTriggerBlock extends TBlock implements TriggerBlock{
	
	private boolean powered;
	
	public OutputTriggerBlock(String ID, Block block, UUID owner) {
		super(ID, block, owner);
	}
	
	public boolean isPowered() {
		return this.powered;
	}
	
	public void setPower(boolean powered) {
		this.powered = powered;
		if(this.block.getChunk().isLoaded()) {
			MineduinoSpigotPlugin.instance.getServer().getPluginManager().callEvent(new SignalEmitEvent(this, this.powered));
		}
	}

	@Override
	public String getID() {
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
