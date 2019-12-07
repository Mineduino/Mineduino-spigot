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
import java.util.*;

public class JsonConfigLocator implements Locator{
    private HashBiMap<String, ArrayList<Location>> cache;
    private String configLoc = "locator.json";
    private Gson gson;

    public JsonConfigLocator() {
        LocationSerializer serializer = new LocationSerializer();
        this.gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(new TypeToken<HashBiMap<String, ArrayList<Location>>>(){}.getType(), new HashBiMapSerializer())
                .registerTypeAdapter(Location.class, serializer).create();
        this.loadFromCache();
    }

    @Override
    public Optional<Location[]> getLocationFor(String identifier, String type) {
        Location[] arr = cache.getOrDefault(makeDefaultKey(identifier, type), new ArrayList<>()).toArray(new Location[0]);
        return Optional.of(arr);
    }

    @Override
    public void setLocationFor(String identifier, String type, Location location) {
        String key = makeDefaultKey(identifier, type);
        if(this.cache.containsKey(key)) {
            this.cache.get(key).add(location);
        } else {
            ArrayList<Location> newArr = new ArrayList<>(Arrays.asList(location));
            this.cache.put(key, newArr);
        }
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
        boolean ret = false;

        Iterator<Map.Entry<String, ArrayList<Location>>> entryIterator = this.cache.entrySet().iterator();
        while(entryIterator.hasNext()) {
            Map.Entry<String, ArrayList<Location>> entry = entryIterator.next();
            if(entry.getValue().remove(loc)) {
                ret = true;
            }
            if(entry.getValue().isEmpty()) {
                entryIterator.remove();
            }

        }
        this.dumpCache();
        return ret;
    }

    @Override
    public boolean isLocationExists(Location loc) {
        return this.cache.containsValue(loc);
    }

    @Override
    public Optional<String> getKeyIfValueExists(Location loc) {
        Optional<String> empty = Optional.empty();
        for(Map.Entry<ArrayList<Location>, String> entry: this.cache.inverse().entrySet()) {
            if(entry.getKey().contains(loc)) {
                return Optional.of(entry.getValue());
            }
        }
        return empty;
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

            this.cache = gson.fromJson(contents, new TypeToken<HashBiMap<String, ArrayList<Location>>>(){}.getType());
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
class HashBiMapSerializer implements JsonSerializer<HashBiMap<String, ArrayList<Location>>>, JsonDeserializer<HashBiMap<String, ArrayList<Location>>> {

    @Override
    public HashBiMap<String, ArrayList<Location>> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashBiMap<String, ArrayList<Location>> hashBiMap = HashBiMap.create();
        JsonObject obj = jsonElement.getAsJsonObject();
        obj.entrySet().forEach((entry) -> {
            hashBiMap.put(entry.getKey(), jsonDeserializationContext.deserialize(entry.getValue(), new TypeToken<ArrayList<Location>>(){}.getType()));

        });
        return hashBiMap;
    }

    @Override
    public JsonElement serialize(HashBiMap<String, ArrayList<Location>> stringLocationHashBiMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        stringLocationHashBiMap.forEach((key, value) -> {
            obj.add(key, jsonSerializationContext.serialize(value));
        });
        return obj;
    }
}

