package eu.razniewski.mineduino.minecrafttoworld;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.locator.Locator;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Material;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Redstone;

import java.util.Optional;

public class PoweredPlaceListener implements Listener {
    @EventHandler
    public void onMineduinoPoweredPlaceListener(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        ItemStack is = null;
        if (e.getHand() == EquipmentSlot.HAND) {
            is = e.getPlayer().getInventory().getItemInMainHand();
        } else if (e.getHand() == EquipmentSlot.OFF_HAND) {
            is = e.getPlayer().getInventory().getItemInOffHand();
        }
        if (is != null) {

            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("MD/") && is.getItemMeta().getDisplayName().endsWith("/o")) {
                if(isPowerable(e.getBlockPlaced().getBlockData())) {
                    Optional<ParsedTopic> parsed = ParsedTopic.from(is.getItemMeta().getDisplayName() + "/" + e.getBlockPlaced().getType().toString());
                    if(!parsed.isPresent()) {
                        return;
                    }
                    if(parsed.get().getInputoutputindicator().equals("o")) {
                        String identifier = parsed.get().getIdentifier();
                        String type = parsed.get().getType();
                        Locator locator = MineduinoPlugin.getInstance().getLocator();
                        if (!locator.isExists(identifier, type)) {
                            e.getPlayer().sendMessage("[MD] Simple output created!");
                            locator.setLocationFor(identifier, type, e.getBlockPlaced().getLocation());
                        } else {
                            e.getPlayer().sendMessage("[MD] This identifier and type already exists!");
                            e.setCancelled(true);
                            return;
                        }
                    }
                }

            }
        }

    }

    public boolean isPowerable(BlockData data) {
        if(data instanceof Powerable) {
            return true;
        } else if(data instanceof AnaloguePowerable) {
            return true;
        } else if(data instanceof RedstoneWallTorch) {
            return true;
        }
        return false;
    }
}
