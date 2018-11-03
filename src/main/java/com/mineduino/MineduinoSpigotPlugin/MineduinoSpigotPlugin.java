package com.mineduino.MineduinoSpigotPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class MineduinoSpigotPlugin extends JavaPlugin{
	
	public static MineduinoSpigotPlugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
	}
	
	@Override
	public void onDisable() {
		
	}
}