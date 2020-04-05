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

public class BlockPlacerListener implements Listener {

    private Material[] signs = new Material[]{Material.SPRUCE_SIGN, Material.ACACIA_SIGN, Material.BIRCH_SIGN, Material.DARK_OAK_SIGN, Material.JUNGLE_SIGN, Material.OAK_SIGN};

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
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
            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("MD/")) {
                if(is.getType().equals(Material.CHEST)) {
                    Optional<ParsedTopic> parsed = ParsedTopic.from(is.getItemMeta().getDisplayName());
                    if (!parsed.isPresent()) {
                        return;
                    }
                    String identifier = parsed.get().getIdentifier();
                    String type = parsed.get().getType();
                    if(type.equals("simple")) {
                        Locator locator = MineduinoPlugin.getInstance().getLocator();
                        e.getPlayer().sendMessage("[MD] Simple input chest created! Topic: MD/" + identifier + "/" + type);
                        locator.setLocationFor(identifier, type, e.getBlockPlaced().getLocation());
                    }
                } else if(is.getType().equals(Material.IRON_BLOCK)) {
                    Optional<ParsedTopic> parsed = ParsedTopic.from(is.getItemMeta().getDisplayName());
                    if (!parsed.isPresent()) {
                        return;
                    }
                    String identifier = parsed.get().getIdentifier();
                    String type = parsed.get().getType();
                    Locator locator = MineduinoPlugin.getInstance().getLocator();
                    e.getPlayer().sendMessage("[MD] Raw input created! Topic: MD/" + identifier + "/" + type);
                    locator.setLocationFor(identifier, type, e.getBlockPlaced().getLocation());

                } else if(is.getType().equals(Material.STONE)) {
                    Optional<ParsedTopic> parsed = ParsedTopic.from(is.getItemMeta().getDisplayName());
                    if (!parsed.isPresent()) {
                        return;
                    }
                    String identifier = parsed.get().getIdentifier();
                    String type = parsed.get().getType();
                    if(type.equals("hidden")) {
                        Locator locator = MineduinoPlugin.getInstance().getLocator();
                        e.getPlayer().sendMessage("[MD] Hidden block created! Topic: MD/" + identifier + "/" + type);
                        locator.setLocationFor(identifier, type, e.getBlockPlaced().getLocation());
                    }
                } else if(isSign(is.getType())) {
                    Optional<ParsedTopic> parsed = ParsedTopic.from(is.getItemMeta().getDisplayName());
                    if (!parsed.isPresent()) {
                        return;
                    }
                    String identifier = parsed.get().getIdentifier();
                    String type = parsed.get().getType();
                    if(type.equals("sign")) {
                        Locator locator = MineduinoPlugin.getInstance().getLocator();
                        e.getPlayer().sendMessage("[MD] Input sign created! Topic: MD/" + identifier + "/" + type);
                        locator.setLocationFor(identifier, type, e.getBlockPlaced().getLocation());
                    }
                }

            }
        }
    }

    private boolean isSign(Material type) {
        for (int i = 0; i < signs.length; i++) {
            if(signs[i].equals(type)) {
                return true;
            }
        }
        return false;
    }
}
