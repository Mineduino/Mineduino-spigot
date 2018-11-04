package com.mineduino.MineduinoSpigotPlugin.Objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import com.mineduino.MineduinoSpigotPlugin.Events.SignalEmitEvent;

public class OutputTriggerBlock extends TriggerBlock{
	
	private int power;
	
	public OutputTriggerBlock(int ID, Block block, int power) {
		super(ID, block);
		this.power = power;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public void emitSignal() {
		//Emites signal to first redstone wire
		Block up = block.getRelative(BlockFace.UP);
		if(up.getType() != Material.REDSTONE_WIRE) {return;}
		RedstoneWire firstWire = (RedstoneWire) up.getBlockData();
		firstWire.setPower(this.power);
		up.setBlockData(firstWire);
		MineduinoSpigotPlugin.instance.getServer().getPluginManager().callEvent(new SignalEmitEvent(this, this.power, up));
	}
}
