package com.mineduino.MineduinoSpigotPlugin.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;

public abstract class ItemUtils {
	
	/*public static ItemStack getItem(Material material, Integer amount, String displayName, String lore) {
		ItemStack is;
		if(amount == null) {is = new ItemStack(material);}
		else {is = new ItemStack(material, amount);}
		if(!(displayName == null && lore == null)) {
			ItemMeta im = MineduinoSpigotPlugin.instance.getServer().getItemFactory().getItemMeta(material);
			if(displayName != null) {
				im.setDisplayName(displayName);
			}
			is.setItemMeta(im);
		}
		return is;
	}
	
	public static ItemStack getItem(Material material, String displayName, String lore) {
		return getItem(material, null, displayName, lore);
	}*/
}