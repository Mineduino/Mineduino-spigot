package eu.razniewski.mineduino.minecrafttoworld;

import com.google.gson.*;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.annotations.MethodTickRunnable;
import eu.razniewski.mineduino.annotations.TickRunnable;
import eu.razniewski.mineduino.locator.Locator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

@TickRunnable(perTicks = 20 * 5)
public class SmartChestUpdater {
    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemStack[].class, new ArrayItemStackSerializator())
            .create();

    @MethodTickRunnable
    public void updateContainers() {
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

        locator = MineduinoPlugin.getInstance().getSmartChestGroupLocator();
        entryIterator = locator.getLoaded().entrySet().iterator();
        while(entryIterator.hasNext()) {
            Map.Entry<String, ArrayList<Location>> entry = entryIterator.next();
            String[] parsed = entry.getKey().split(";");
            String forTopic = "MD/" + parsed[0] + "/" + parsed[1];

            Bukkit.getScheduler().runTaskLater(MineduinoPlugin.getInstance(), new GroupUpdater(entry.getValue(), forTopic), 1);
        }
    }
}

class ArrayItemStackSerializator implements JsonSerializer<ItemStack[]> {

    @Override
    public JsonElement serialize(ItemStack[] itemStacks, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray arr = new JsonArray();
        for (int i = 0; i < itemStacks.length; i++) {
            if(itemStacks[i] != null) {
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
                String jsoned = SmartChestUpdater.gson.toJson(stacks, ItemStack[].class);
                MineduinoPlugin.getMqttHandler().standardPublish(topic, jsoned);
            }
        }

    }
}

class SimpleItemStack implements Serializable {
    private String material;
    private int amount;

    public SimpleItemStack(String material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleItemStack that = (SimpleItemStack) o;
        return amount == that.amount &&
                Objects.equals(material, that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, amount);
    }

    @Override
    public String toString() {
        return "SimpleItemStack{" +
                "material='" + material + '\'' +
                ", amount=" + amount +
                '}';
    }
}

class GroupUpdater implements Runnable {
    private ArrayList<Location> locs;
    private String topic;

    public GroupUpdater(ArrayList<Location> locs, String topic) {
        this.locs = locs;
        this.topic = topic;
    }


    @Override
    public void run() {
        /* Can't use here generic Map because of Serialization issues */
        HashMap<String, Integer> itemMap = new HashMap<>();
        for (Location loc: locs) {
            Block b = loc.getBlock();
            if(b.getState() instanceof Container) {
                Container container = (Container) b.getState();
                ItemStack[] stacks = container.getInventory().getStorageContents();
                for (ItemStack stack :
                        stacks) {
                    if(stack != null) {
                        String material = stack.getType().name();
                        if (itemMap.containsKey(material)) {
                            itemMap.put(material, itemMap.get(material) + stack.getAmount());
                        } else {
                            itemMap.put(material, stack.getAmount());
                        }
                    }
                }
            }

        }
        List<SimpleItemStack> itemStacks = new ArrayList<>();
        itemMap.entrySet().forEach((stringIntegerEntry -> {
            itemStacks.add(new SimpleItemStack(stringIntegerEntry.getKey(), stringIntegerEntry.getValue()));
        }));
        String jsoned = SmartChestUpdater.gson.toJson(itemStacks);
        MineduinoPlugin.getMqttHandler().standardPublish(topic, jsoned);


    }
}


