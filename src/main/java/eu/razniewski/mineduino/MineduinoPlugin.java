package eu.razniewski.mineduino;

import eu.razniewski.mineduino.config.CachedJsonFileConfigManager;
import eu.razniewski.mineduino.config.ConfigManager;
import eu.razniewski.mineduino.connector.MqttHandler;
import eu.razniewski.mineduino.locator.JsonConfigLocator;
import eu.razniewski.mineduino.locator.Locator;
import eu.razniewski.mineduino.minecrafttoworld.OutputerBlockBreakListener;
import eu.razniewski.mineduino.minecrafttoworld.PoweredPlaceListener;
import eu.razniewski.mineduino.minecrafttoworld.RedstoneBlockListener;
import eu.razniewski.mineduino.worldtominecraft.ChestDestroyListener;
import eu.razniewski.mineduino.worldtominecraft.ChestPlacerListener;
import eu.razniewski.mineduino.worldtominecraft.WorldToMinecraftListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MineduinoPlugin extends JavaPlugin {

    ConfigManager manager;
    private static MineduinoPlugin instance;
    private static MqttHandler mqttHandler;
    Locator locator;
    @Override
    public void onEnable() {
        instance = this;
        manager = new CachedJsonFileConfigManager("mineduino.json");
        this.locator = new JsonConfigLocator();
        if(!manager.<Double>getValue("version").isPresent()) {
            manager.setValue("broker", "tcp://mineduino.eu:1883");
            manager.setValue("version", 0.1);
        }
        this.mqttHandler = new MqttHandler();
        this.mqttHandler.connectTo(manager.<String>getValue("broker").get());

        getServer().getPluginManager().registerEvents(new WorldToMinecraftListener(), this);
        getServer().getPluginManager().registerEvents(new ChestPlacerListener(), this);
        getServer().getPluginManager().registerEvents(new ChestDestroyListener(), this);
        getServer().getPluginManager().registerEvents(new RedstoneBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PoweredPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new OutputerBlockBreakListener(), this);


    }
    @Override
    public void onDisable() {
    }

    public ConfigManager getManager() {
        return manager;
    }

    public static MqttHandler getMqttHandler() {
        return mqttHandler;
    }

    public Locator getLocator() {
        return locator;
    }

    public static MineduinoPlugin getInstance() {
        return instance;
    }
}
