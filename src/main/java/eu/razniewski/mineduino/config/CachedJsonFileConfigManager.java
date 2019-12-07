package eu.razniewski.mineduino.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class CachedJsonFileConfigManager implements ConfigManager{

    private HashMap<String, Object> cache;
    private Gson gson;
    private String configLoc;

    public CachedJsonFileConfigManager(String location) {
        this.configLoc = location;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.loadFromCache();

    }

    public <T extends Serializable> Optional<T> getValue(String key) {
        return Optional.ofNullable((T) this.cache.get(key));
    }

    public <T extends Serializable> boolean setValue(String key, T value) {
        this.cache.put(key, value);
        this.dumpCache();
        return true;
    }

    @Override
    public boolean exists(String key) {
        return this.cache.containsKey(key);
    }

    @Override
    public boolean delete(String key) {
        this.cache.remove(key);
        this.dumpCache();
        return true;
    }

    @Override
    public <T extends Serializable> boolean removeIf(T value) {
        this.cache.entrySet().removeIf(obj -> obj.getValue().equals(value));
        return true;
    }

    @Override
    public <T extends Serializable> boolean isValueExists(T value) {
        return this.cache.containsValue(value);
    }

    @Override
    public <T extends Serializable> Optional<String> getValueKeyIfExists(T value) {

        for (Map.Entry<String, Object> entry : this.cache.entrySet()) {
            if (entry.getValue().equals(value)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public <T extends Serializable> Optional<String> getValueKeyIfPrediction(Predicate<Object> predicate) {
        for (Map.Entry<String, Object> entry : this.cache.entrySet()) {
            if(predicate.test(entry.getValue())) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
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
            System.out.println(contents);
            this.cache = gson.fromJson(contents, new TypeToken<HashMap<String, Object>>(){}.getType());
        } catch (IOException e) {
            this.cache = new HashMap<>();
        }

    }
}
