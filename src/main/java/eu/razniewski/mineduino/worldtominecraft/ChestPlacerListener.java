package eu.razniewski.mineduino.worldtominecraft;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.locator.Locator;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ChestPlacerListener implements Listener {
    @EventHandler
    public void onChestPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        ItemStack is = null;
        if (e.getHand() == EquipmentSlot.HAND) {
            is = e.getPlayer().getInventory().getItemInMainHand();
        } else if (e.getHand() == EquipmentSlot.OFF_HAND) {
            is = e.getPlayer().getInventory().getItemInOffHand();
        }
        if (is != null && is.getType().equals(Material.CHEST)) {
            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("MD/")) {

                Optional<ParsedTopic> parsed = ParsedTopic.from(is.getItemMeta().getDisplayName());
                if(!parsed.isPresent()) {
                    return;
                }
                String identifier = parsed.get().getIdentifier();
                String type = parsed.get().getType();
                Locator locator = MineduinoPlugin.getInstance().getLocator();
                if(!locator.isExists(identifier, type)) {
                    e.getPlayer().sendMessage("[MD] Simple input chest created!");
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
