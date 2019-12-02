package eu.razniewski.mineduino.locator;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import eu.razniewski.mineduino.utils.StandarizedUtils;
import org.bukkit.Location;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonConfigLocator implements Locator{
    private HashBiMap<String, Location> cache;
    private String configLoc = "locator.json";
    private Gson gson;

    public JsonConfigLocator() {
        LocationSerializer serializer = new LocationSerializer();
        this.gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(new TypeToken<HashBiMap<String, Location>>(){}.getType(), new HashBiMapSerializer())
                .registerTypeAdapter(Location.class, serializer).create();
        this.loadFromCache();
    }

    @Override
    public Optional<Location> getLocationFor(String identifier, String type) {
        return Optional.ofNullable(cache.getOrDefault(identifier, null));
    }

    @Override
    public void setLocationFor(String identifier, String type, Location location) {
        this.cache.put(makeDefaultKey(identifier, type), location);
        this.dumpCache();

    }

    @Override
    public boolean isExists(String identifier, String type) {
        return this.cache.containsKey(makeDefaultKey(identifier, type));
    }

    @Override
    public boolean delete(String identifier, String type) {
        this.cache.remove(makeDefaultKey(identifier, type));
        this.dumpCache();
        return true;
    }

    @Override
    public boolean delete(String key) {
        this.cache.remove(key);
        this.dumpCache();
        return true;
    }

    @Override
    public boolean removeAll(Location loc) {
        this.cache.entrySet().removeIf(obj -> obj.getValue().equals(loc));
        this.dumpCache();
        return true;
    }

    @Override
    public boolean isLocationExists(Location loc) {
        return this.cache.containsValue(loc);
    }

    @Override
    public Optional<String> getKeyIfValueExists(Location loc) {
        return Optional.ofNullable(this.cache.inverse().getOrDefault(loc, null));
    }

    private String makeDefaultKey(String i, String t) {
        return StandarizedUtils.makeEntryKeyFromData(i, t);
    }
    private void dumpCache() {
        try (PrintWriter out = new PrintWriter(configLoc)) {
            out.println(gson.toJson(cache));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadFromCache() {
        try {
            String contents = new String(Files.readAllBytes(Paths.get(configLoc)));

            this.cache = gson.fromJson(contents, new TypeToken<HashBiMap<String, Location>>(){}.getType());
        } catch (IOException e) {
            this.cache = HashBiMap.create();
        }

    }
}
class LocationSerializer implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(location.serialize(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<String, Object> obj = jsonDeserializationContext.deserialize(jsonElement, new TypeToken<HashMap<String, Object>>(){}.getType());
        return Location.deserialize(obj);
    }
}
class HashBiMapSerializer implements JsonSerializer<HashBiMap<String, Location>>, JsonDeserializer<HashBiMap<String, Location>> {

    @Override
    public HashBiMap<String, Location> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashBiMap<String, Location> hashBiMap = HashBiMap.create();
        JsonObject obj = jsonElement.getAsJsonObject();
        obj.entrySet().forEach((entry) -> {
            hashBiMap.put(entry.getKey(), jsonDeserializationContext.deserialize(entry.getValue(), Location.class));

        });
        return hashBiMap;
    }

    @Override
    public JsonElement serialize(HashBiMap<String, Location> stringLocationHashBiMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        stringLocationHashBiMap.forEach((key, value) -> {
            obj.add(key, jsonSerializationContext.serialize(value));
        });
        return obj;
    }
}

