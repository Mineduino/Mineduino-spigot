package eu.razniewski.mineduino;

import eu.razniewski.mineduino.annotations.TickInvoker;
import eu.razniewski.mineduino.annotations.TickRunnableUtil;
import eu.razniewski.mineduino.config.CachedJsonFileConfigManager;
import eu.razniewski.mineduino.config.ConfigManager;
import eu.razniewski.mineduino.connector.MqttHandler;
import eu.razniewski.mineduino.locator.JsonConfigLocator;
import eu.razniewski.mineduino.locator.Locator;
import eu.razniewski.mineduino.minecrafttoworld.OutputerBlockBreakListener;
import eu.razniewski.mineduino.minecrafttoworld.PoweredPlaceListener;
import eu.razniewski.mineduino.minecrafttoworld.RedstoneBlockListener;
import eu.razniewski.mineduino.worldtominecraft.ChestDestroyListener;
import eu.razniewski.mineduino.worldtominecraft.BlockPlacerListener;
import eu.razniewski.mineduino.worldtominecraft.EntityControllerListener;
import eu.razniewski.mineduino.worldtominecraft.WorldToMinecraftListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MineduinoPlugin extends JavaPlugin {

    ConfigManager manager;
    private static MineduinoPlugin instance;
    private static MqttHandler mqttHandler;
    Locator locator;
    Locator smartLocator;
    @Override
    public void onEnable() {
        File f = new File("plugins/mineduino");
        if(!f.exists())
            f.mkdir();
        instance = this;
        manager = new CachedJsonFileConfigManager("plugins/mineduino/config.json");
        this.locator = new JsonConfigLocator("plugins/mineduino/locator.json");
        this.smartLocator = new JsonConfigLocator("plugins/mineduino/smartchest.json");
        if(!manager.<Double>getValue("version").isPresent()) {
            manager.setValue("broker", "tcp://mineduino.eu:1883");
            manager.setValue("broker_login_username", "username");
            manager.setValue("broker_login_password", "password");
            manager.setValue("version", 1.1);
        }
        this.mqttHandler = new MqttHandler();
        this.mqttHandler.connectTo(manager.<String>getValue("broker").get());

        getServer().getPluginManager().registerEvents(new WorldToMinecraftListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlacerListener(), this);
        getServer().getPluginManager().registerEvents(new ChestDestroyListener(), this);
        getServer().getPluginManager().registerEvents(new RedstoneBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PoweredPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new OutputerBlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new EntityControllerListener(), this);

        for(TickInvoker invoker: TickRunnableUtil.getInvocators()) {

            Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, invoker, 20*30, invoker.getTick());
        }


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

    public Locator getSmartChestLocator() { return smartLocator; }

    public static MineduinoPlugin getInstance() {
        return instance;
    }
}
