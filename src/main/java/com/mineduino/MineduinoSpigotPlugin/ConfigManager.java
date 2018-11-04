package com.mineduino.MineduinoSpigotPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

abstract class ConfigManager {
	
	static File mainFolder;
	
	static void load(){
		mainFolder = MineduinoSpigotPlugin.instance.getDataFolder();
		if(!mainFolder.exists()){
			mainFolder.mkdir();
			mainFolder = MineduinoSpigotPlugin.instance.getDataFolder();
		}
	}
	
	static Config getConfig(String name, File folder, boolean copyFromResourceIfNotCreated){
		String fileName = name + ".yml";
		if(folder == null) {
			folder = mainFolder;
		}
		File file = new File(folder, fileName);
		if(!file.exists()){
			if(copyFromResourceIfNotCreated){
				try {
					Files.copy(MineduinoSpigotPlugin.instance.getResource(fileName), file.toPath());
				}
				catch (IOException ex) {
					ex.printStackTrace();
					return null;
				}
				file = new File(folder, fileName);
			}
			else if(!file.exists()){
				return null;
			}
		}
		return new Config(file);
	}
	
	static File createNewFile(String name, String directory){
		String fileName = name + ".yml";
		File folder = getFolder(directory);
		File file = new File(folder, fileName);
		try {
			file.createNewFile();
			return file;
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	static HashMap<String, Config> getAllConfigsFromFolder(String directory){
		HashMap<String, Config> cfgs = new HashMap<String, Config>();
		File folder = getFolder(directory);
		String[] files = folder.list();
		if(files.length == 0){
			return null;
		}
		for(String fs: files) {
			String s = fs.replaceAll(".yml", "");
			Config cfg = getConfig(s, folder, false);
			cfgs.put(s, cfg);
		}
		return cfgs;
	}
	
	static File getFolder(String directory){
		if(directory != null){
			File folder = new File(mainFolder+directory);
			if(!folder.exists()){
				folder.mkdir();
			}
			return folder;
		}
		else{
			return mainFolder;
		}
	}
	
}
