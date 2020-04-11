package eu.razniewski.mineduino.worldtominecraft.evaluators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.connector.MineduinoMessageEvent;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

public class SignEvaluator implements Consumer<MineduinoMessageEvent> {
    private Gson gsonEvaluator = new GsonBuilder().create();
    private String[] defaultStringArray = new String[]{"","","",""};

    String[] fromByteArray(byte[] bytes) {
        try {
            String parsed = new String(bytes, StandardCharsets.UTF_8);
            String[] arrayParsed = gsonEvaluator.fromJson(parsed, String[].class);
            if(arrayParsed != null) {
                return arrayParsed;
            } else {
                return defaultStringArray;
            }
        } catch (Exception e) {
            return defaultStringArray;
        }
    }

    @Override
    public void accept(MineduinoMessageEvent mineduinoMessageEvent) {

        Optional<ParsedTopic> parsed = ParsedTopic.from(mineduinoMessageEvent.getTopic());
        if(!parsed.isPresent()) {
            return;
        }
        Optional<Location[]> signLoc = MineduinoPlugin.getInstance().getLocator().getLocationFor(parsed.get().getIdentifier(), parsed.get().getType());
        if(signLoc.isPresent()) {
            for(Location loc: signLoc.get()){
                String[] value = fromByteArray(mineduinoMessageEvent.getMessage());
                Bukkit.getScheduler().runTask(MineduinoPlugin.getInstance(), new SetProperSign(loc, value));
            }
        } else {
            MineduinoPlugin.getInstance().getLogger().warning("[SIMPLE] No location for: " + parsed.get().getIdentifier());
        }

    }
}
class SetProperSign implements Runnable {
    private Location location;
    private String[] value;

    public SetProperSign(Location location, String[] value) {
        this.location = location;
        this.value = value;
    }

    @Override
    public void run() {
        Block at = this.location.getBlock();
        if(at.getState() instanceof Sign) {
            Sign sign = (Sign) at.getState();
            sign.update(true);
            for (int i = 0; i < value.length && i < 4; i++) {
                sign.setLine(i, ChatColor.translateAlternateColorCodes('&', value[i]));
            }
            if(value.length < 4) {
                for (int i = 3; i >= value.length; i--) {
                    sign.setLine(i, "");
                }
            }
            sign.update();

        } else {
            MineduinoPlugin.getInstance().getLocator().removeAll(location);
            at.breakNaturally();
        }

    }
}