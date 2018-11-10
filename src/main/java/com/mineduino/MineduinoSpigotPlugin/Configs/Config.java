package com.mineduino.MineduinoSpigotPlugin.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends YamlConfiguration{
	private File file;
	Config(File file){
		super();
		this.file = file;
		loadConfig();
	}
	public File getFile(){
		return this.file;
	}
	private boolean loadConfig(){
		try {
			load(file);
			return true;
		} 
		catch (IOException | InvalidConfigurationException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}
	public boolean saveConfig(){
		try {
			save(file);
			return true;
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public boolean reload() {
		return loadConfig();
	}
}
