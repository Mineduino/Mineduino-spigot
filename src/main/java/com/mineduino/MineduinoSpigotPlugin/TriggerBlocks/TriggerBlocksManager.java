package com.mineduino.MineduinoSpigotPlugin.TriggerBlocks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Chunk;

import com.mineduino.MineduinoSpigotPlugin.Configs.Config;
import com.mineduino.MineduinoSpigotPlugin.Configs.ConfigManager;

public abstract class TriggerBlocksManager{
	
	/*
	private static File folder;
	
	HashMap<Chunk, ArrayList<TriggerBlock>> triggersByChunks = new HashMap<Chunk, ArrayList<TriggerBlock>>();
	HashMap<String, TriggerBlock> triggersByID = new HashMap<String, TriggerBlock>();
	
	public static void load() {
		folder = ConfigManager.getFolder("/storage/");
	}
	
	public static void chunkLoaded(Chunk chunk) {
		String name = chunk.getX()+"_"+chunk.getZ()+"_"+chunk.getWorld().getName();
		Config cfg = ConfigManager.getConfig(name, folder, false);
		if(cfg == null) {
			return;
		}
		
		
		
	}
	
	public static void chunkClosed(Chunk chunk) {
		
	}
	
	public static void newTriggerBlockPlaced(TriggerBlock tB) {
		
	}
	*/
	
}
