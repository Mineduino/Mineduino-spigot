package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.mineduino.MineduinoSpigotPlugin.Events.SignalEmitEvent;

public class SignalEmitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void signalEmitListener(SignalEmitEvent e) {
        if (!e.isCancelled()) {
            Block b = e.getOutputBlock().getFirstWireBlock();
            RedstoneWire wire = (RedstoneWire) b.getBlockData();
            int pow = e.getPower();
            int oldpow = e.getOldPower();
            wire.setPower(pow);
            b.setBlockData(wire);
            if (oldpow > 0 && pow == 0) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1f, 1f);
            } else if (oldpow == 0 && pow > 0) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1f, 1f);
            } else if (oldpow > 0 && pow > 0) {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1f, 1f);
            }
        }
    }
}
