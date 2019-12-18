package eu.razniewski.mineduino.locator;

import com.google.common.collect.HashBiMap;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Optional;

public interface Locator {
    Optional<Location[]> getLocationFor(String identifier, String type);
    void setLocationFor(String identifier, String type, Location location);
    boolean isExists(String identifier, String type);
    boolean delete(String identifier, String type);
    boolean delete(String key);
    boolean removeAll(Location loc);
    boolean isLocationExists(Location loc);
    HashBiMap<String, ArrayList<Location>> getLoaded();
    Optional<String> getKeyIfValueExists(Location loc);

}
