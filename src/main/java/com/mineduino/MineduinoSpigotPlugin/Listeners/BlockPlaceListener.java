package com.mineduino.MineduinoSpigotPlugin.Listeners;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void blockPlaceListener(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        ItemStack is = null;
        if (e.getHand() == EquipmentSlot.HAND) {
            is = e.getPlayer().getInventory().getItemInMainHand();
        } else if (e.getHand() == EquipmentSlot.OFF_HAND) {
            is = e.getPlayer().getInventory().getItemInOffHand();
        }
        if (is != null && is.getType().isSolid() && !is.getType().isBurnable() && !is.getType().hasGravity()) {
            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("MD;I;")) {
                String topic = e.getItemInHand().getItemMeta().getDisplayName().split(";")[2];
                MineduinoSpigotPlugin.getInputStorager().add(e.getBlock(), topic);
            } else if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("MD;O;")) {
                String topic = e.getItemInHand().getItemMeta().getDisplayName().split(";")[2];
                MineduinoSpigotPlugin.getOutputStorager().add(topic, e.getBlock());

            }
        }
    }
}
