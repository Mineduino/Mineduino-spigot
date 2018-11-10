package com.mineduino.MineduinoSpigotPlugin.Configs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;

public class MainConfig{
	
	private Config cfg;
	
	private ItemStack inputTriggerBlockItem;
	private ItemStack outputTriggerBlockItem;
	
	public MainConfig() {
		this.cfg = ConfigManager.getConfig("config", null, true);
		
		String mat1 = cfg.getString("InputTriggerBlockMaterial");
		Material item1mat = Material.valueOf(mat1);
		if(!item1mat.name().equalsIgnoreCase(mat1)) {
			
		}
		if(item1mat.isBlock() && item1mat.isSolid() && !item1mat.hasGravity()) {
			inputTriggerBlockItem = new ItemStack(item1mat);
			String item1dname = colourText(cfg.getString("InputTriggerBlockItemData.name"));
			String item1lore = cfg.getString("InputTriggerBlockItemData.lore");
			ItemMeta item1meta = MineduinoSpigotPlugin.instance.getServer().getItemFactory().getItemMeta(item1mat);
			item1meta.setDisplayName(item1dname);
			inputTriggerBlockItem.setItemMeta(item1meta);
		}
		else {
			
		}
		
		String mat2 = cfg.getString("OutputTriggerBlockMaterial");
		Material item2mat = Material.valueOf(mat2);
		if(!item2mat.name().equalsIgnoreCase(mat2)) {
			
		}
		if(item2mat.isBlock() && item2mat.isSolid() && !item2mat.hasGravity()) {
			outputTriggerBlockItem = new ItemStack(item2mat);
			String item2dname = colourText(cfg.getString("OutputTriggerBlockItemData.name"));
			String item2lore = cfg.getString("OutputTriggerBlockItemData.lore");
			ItemMeta item2meta = MineduinoSpigotPlugin.instance.getServer().getItemFactory().getItemMeta(item2mat);
			item2meta.setDisplayName(item2dname);
			outputTriggerBlockItem.setItemMeta(item2meta);
		}
		else {
			
		}
	}
	
	public String colourText(String txt) {
		if(txt == null) {return null;}
		else {
			return new String(txt.replaceAll("&(0-9|a-f|i|l|m-o)", "\\§$1"));
		}
	}
}