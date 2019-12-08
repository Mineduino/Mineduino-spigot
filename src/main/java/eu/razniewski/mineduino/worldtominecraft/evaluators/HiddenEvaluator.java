package eu.razniewski.mineduino.worldtominecraft.evaluators;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Optional;
import java.util.function.Consumer;

public class HiddenEvaluator implements Consumer<MineduinoMessageEvent> {
    int fromByteArray(byte[] bytes) {
        return bytes[0];
    }
    @Override
    public void accept(MineduinoMessageEvent mineduinoMessageEvent) {

        Optional<ParsedTopic> parsed = ParsedTopic.from(mineduinoMessageEvent.getTopic());
        if(!parsed.isPresent()) {
            return;
        }
        Optional<Location[]> blockLoc = MineduinoPlugin.getInstance().getLocator().getLocationFor(parsed.get().getIdentifier(), parsed.get().getType());
        if(blockLoc.isPresent()) {
            for(Location loc: blockLoc.get()) {
                int value = fromByteArray(mineduinoMessageEvent.getMessage());
                Bukkit.getScheduler().runTask(MineduinoPlugin.getInstance(), new HiddenBlocker(loc, value));
            }
        } else {
            MineduinoPlugin.getInstance().getLogger().warning("[HIDDEN] No location for: " + parsed.get().getIdentifier());
        }

    }
}

class HiddenBlocker implements Runnable {
    private Location location;
    private int value;

    public HiddenBlocker(Location location, int value) {
        this.location = location;
        this.value = value;
    }

    @Override
    public void run() {
        if(value > 0) {
            Block at = this.location.getBlock();
            if(at.getType().equals(Material.AIR)) {
                return;
            } else if(at.getType().equals(Material.STONE)) {
                at.setType(Material.AIR);
            }
        } else {
            Block at = this.location.getBlock();
            if(at.getType().equals(Material.STONE)) {
                return;
            } else if(at.getType().equals(Material.AIR)) {
                at.setType(Material.STONE);
            }
        }

    }
}
