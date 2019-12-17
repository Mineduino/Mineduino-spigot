package eu.razniewski.mineduino.minecrafttoworld;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.annotations.MethodTickRunnable;
import eu.razniewski.mineduino.annotations.TickRunnable;
import eu.razniewski.mineduino.locator.Locator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@TickRunnable(perTicks = 20 * 5)
public class SmartChestUpdater {
    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemStack[].class, new ArrayItemStackSerializator())
            .create();

    @MethodTickRunnable
    public void updateContainers() {
        System.out.println("CONTAINERUPDATE");
        Locator locator = MineduinoPlugin.getInstance().getSmartChestLocator();

        Iterator<Map.Entry<String, ArrayList<Location>>> entryIterator = locator.getLoaded().entrySet().iterator();
        while(entryIterator.hasNext()) {
            Map.Entry<String, ArrayList<Location>> entry = entryIterator.next();
            for (int i = 0; i < entry.getValue().size(); i++) {
                Location location = entry.getValue().get(i);
                String[] parsed = entry.getKey().split(";");
                String forTopic = "MD/" + parsed[0] + "/" + parsed[1];
                Bukkit.getScheduler().runTaskLater(MineduinoPlugin.getInstance(), new Updater(location, forTopic), 1);

            }
        }
    }
}

class ArrayItemStackSerializator implements JsonSerializer<ItemStack[]> {

    @Override
    public JsonElement serialize(ItemStack[] itemStacks, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray arr = new JsonArray();
        for (int i = 0; i < itemStacks.length; i++) {
            if(itemStacks[i] != null) {
                MineduinoPlugin.getInstance().getLogger().warning(itemStacks[i].toString());
                ItemStack stack = itemStacks[i];
                JsonObject obj = new JsonObject();
                obj.addProperty("material", stack.getType().name());
                obj.addProperty("amount", stack.getAmount());
                obj.addProperty("slot", i);
                arr.add(obj);
            }
        }
        return arr;
    }
}

class Updater implements Runnable {

    private Location loc;
    private String topic;
    public Updater(Location loc, String topic) {
        this.loc = loc;
        this.topic = topic;
    }

    @Override
    public void run() {
        Block b = loc.getBlock();
        if(b.getState() instanceof Container) {
            Container container = (Container) b.getState();
            ItemStack[] stacks = container.getInventory().getStorageContents();
            if(stacks != null) {
                System.out.println("KACZKA");
                for (int i = 0; i < stacks.length; i++) {
                    System.out.println(stacks[i]);

                }
                System.out.println(SmartChestUpdater.gson);
                String jsoned = SmartChestUpdater.gson.toJson(stacks, ItemStack[].class);
                MineduinoPlugin.getInstance().getLogger().warning(jsoned);

                MineduinoPlugin.getMqttHandler().standardPublish(topic, jsoned);
            }
        }

    }
}
