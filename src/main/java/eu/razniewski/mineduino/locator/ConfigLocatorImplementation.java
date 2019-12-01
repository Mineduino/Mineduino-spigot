package eu.razniewski.mineduino.locator;

import eu.razniewski.mineduino.config.CachedJsonFileConfigManager;
import eu.razniewski.mineduino.config.ConfigManager;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigLocatorImplementation implements Locator {
    private String filename = "locator.json";
    private ConfigManager manager;

    public ConfigLocatorImplementation() {
        this.manager = new CachedJsonFileConfigManager(filename);
    }

    @Override
    public Optional<Location> getLocationFor(String identifier, String type) {
        Optional<HashMap<String, Object>> opt = manager.getValue(identifier + ";" + type);
        if(opt.isPresent()) {
            return Optional.of(Location.deserialize(opt.get()));
        }
        return Optional.empty();
    }

    @Override
    public void setLocationFor(String identifier, String type, Location location) {
        HashMap<String, Object> serialized = (HashMap<String, Object>) location.serialize();
        this.manager.setValue(identifier + ";" + type, serialized);
    }

    @Override
    public boolean isExists(String identifier, String type) {
        return this.manager.exists(identifier + ";" + type);
    }

    @Override
    public boolean delete(String identifier, String type) {
        return this.manager.delete(identifier + ";" + type);
    }

    @Override
    public boolean removeAll(Location loc) {
        return this.manager.removeIf((HashMap<String, Object>) loc.serialize());
    }

    @Override
    public boolean isLocationExists(Location loc) {
        return this.manager.isValueExists((HashMap<String, Object>) loc.serialize());
    }


}
