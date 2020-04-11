package eu.razniewski.mineduino.worldtominecraft.evaluators;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

public class SimpleEvaluator implements Consumer<MineduinoMessageEvent> {
    int fromByteArray(byte[] bytes) {
        return bytes[0];
    }
    @Override
    public void accept(MineduinoMessageEvent mineduinoMessageEvent) {

        Optional<ParsedTopic> parsed = ParsedTopic.from(mineduinoMessageEvent.getTopic());
        if(!parsed.isPresent()) {
            return;
        }
        Optional<Location[]> chestLoc = MineduinoPlugin.getInstance().getLocator().getLocationFor(parsed.get().getIdentifier(), parsed.get().getType());
        if(chestLoc.isPresent()) {
            for(Location loc: chestLoc.get()){
                int value = fromByteArray(mineduinoMessageEvent.getMessage());
                Bukkit.getScheduler().runTask(MineduinoPlugin.getInstance(), new SetProperItemsRunnable(loc, value));
            }
        } else {
            MineduinoPlugin.getInstance().getLogger().warning("[SIMPLE] No location for: " + parsed.get().getIdentifier());
        }

    }
}
class SetProperItemsRunnable implements Runnable {
    private Location location;
    private int value;

    public SetProperItemsRunnable(Location location, int value) {
        this.location = location;
        this.value = value;
    }

    @Override
    public void run() {
        Block at = this.location.getBlock();
        if(at.getType().equals(Material.CHEST)) {
            InventoryHolder chest = (InventoryHolder) at.getState();
            Inventory inv = chest.getInventory();
            inv.setContents(getProperItemStack(value, inv.getSize()));

        } else {
            MineduinoPlugin.getInstance().getLocator().removeAll(location);
            at.breakNaturally();
        }

    }

    public ItemStack[] getProperItemStack(int value, int maxValue) {
        ItemStack[] stack = null;
        if(value >= maxValue) {
            stack = new ItemStack[maxValue];
        } else {
            stack = new ItemStack[value];
        }
        for (int i = 0; i < stack.length; i++) {
            stack[i] = new ItemStack(Material.WOODEN_HOE, 1);
            stack[i].getItemMeta().setDisplayName("MINEDUINO");
        }
        return stack;

    }
}
