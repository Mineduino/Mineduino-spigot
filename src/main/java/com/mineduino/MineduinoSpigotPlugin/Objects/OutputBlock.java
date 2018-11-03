package com.mineduino.MineduinoSpigotPlugin.Objects;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.RedstoneWire.Connection;

public class OutputBlock{
	
	private int ID;
	private Block block;
	private int power;
	private ArrayList<Block> connectedWires = new ArrayList<Block>();
	
	public OutputBlock(int ID, Block block, int power) {
		this.ID = ID;
		this.block = block;
		this.power = power;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public void emitSignal() {
		Block up = block.getRelative(BlockFace.UP);
		if(up.getType() != Material.REDSTONE_WIRE) {return;}
		RedstoneWire firstWire = (RedstoneWire) up.getBlockData();
		for(BlockFace bf: firstWire.getAllowedFaces()) {
			Connection con = firstWire.getFace(bf);
			if(con == Connection.NONE) {continue;}
			Block cb = up.getRelative(bf);
			if(connectedWires.contains(cb)) {continue;}
			connectedWires.add(cb);
		}
	}
}
