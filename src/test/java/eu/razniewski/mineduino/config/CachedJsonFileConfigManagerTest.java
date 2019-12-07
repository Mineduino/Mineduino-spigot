package eu.razniewski.mineduino.config;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CachedJsonFileConfigManagerTest {

    @org.junit.jupiter.api.Test
    void getValue() {
        UUID uuid = UUID.randomUUID();
        ConfigManager manager = new CachedJsonFileConfigManager("/tmp/" + uuid.toString());
        Optional<Double> test = manager.<Double>getValue("test");
        assertTrue(!test.isPresent());

    }

    @org.junit.jupiter.api.Test
    void setValue() {
        UUID uuid = UUID.randomUUID();
        ConfigManager manager = new CachedJsonFileConfigManager("/tmp/" + uuid.toString());
        Optional<Double> test = manager.<Double>getValue("test");
        assertTrue(!test.isPresent());

        manager.<Double>setValue("test", Double.valueOf(2.0));
        test = manager.<Double>getValue("test");
        assertTrue(test.isPresent());
        assertTrue(test.get() == 2.0);

        manager = new CachedJsonFileConfigManager("/tmp/test123.json");
        test = manager.<Double>getValue("test");
        assertTrue(test.isPresent());
        assertTrue(test.get() == 2.0);


        manager.setValue("test123", 1);

        Optional<Integer> test2 = manager.<Integer>getValue("test123");
        assertTrue(test2.isPresent());
        assertTrue(test2.get() == 1);


        manager.setValue("test1234", "Blabla");

        Optional<String> test3 = manager.<String>getValue("test1234");
        assertTrue(test3.isPresent());
        assertTrue(test3.get().equals("Blabla"));


    }
}