package com.mineduino.MineduinoSpigotPlugin;

import org.bukkit.plugin.java.JavaPlugin;

import com.mineduino.MineduinoSpigotPlugin.Handlers.SignalHandler;

public class MineduinoSpigotPlugin extends JavaPlugin{
	
	public static MineduinoSpigotPlugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		instance.getServer().getPluginManager().registerEvents(new SignalHandler(), instance);
		
		
	}
	
	@Override
	public void onDisable() {
		
	}
}