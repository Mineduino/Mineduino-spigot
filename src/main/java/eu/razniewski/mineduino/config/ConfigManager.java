package eu.razniewski.mineduino.config;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ConfigManager {
    public <T extends Serializable> Optional<T> getValue(String key);
    public <T extends Serializable> boolean setValue(String key, T value);
    public boolean exists(String key);
    public boolean delete(String key);

    public <T extends Serializable> boolean removeIf(T value);
    public <T extends Serializable> boolean isValueExists(T value);
    public <T extends Serializable> Optional<String> getValueKeyIfExists(T value);
    public <T extends Serializable> Optional<String> getValueKeyIfPrediction(Predicate<Object> predicate);

}
