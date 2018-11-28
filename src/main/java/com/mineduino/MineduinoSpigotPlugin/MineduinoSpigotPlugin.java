package com.mineduino.MineduinoSpigotPlugin;

import com.mineduino.MineduinoSpigotPlugin.Callbacks.MessageCallback;
import com.mineduino.MineduinoSpigotPlugin.Configs.ConfigManager;
import com.mineduino.MineduinoSpigotPlugin.Configs.MainConfig;
import com.mineduino.MineduinoSpigotPlugin.Listeners.BlockBreakListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.BlockPlaceListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.BlockRedstoneListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.MQTTCallbackListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.MQTTPublishListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.SignalEmitListener;
import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.TriggerBlocksManager;
import com.mineduino.MineduinoSpigotPlugin.Utils.InputStorager;
import com.mineduino.MineduinoSpigotPlugin.Utils.InputStoragerInMemory;
import com.mineduino.MineduinoSpigotPlugin.Utils.OutputStoragerInMemory;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.mineduino.MineduinoSpigotPlugin.Utils.OutputStorager;

public class MineduinoSpigotPlugin extends JavaPlugin {

    public static MineduinoSpigotPlugin instance;
    public static MainConfig mainCfg;
    public static MqttClient client;
    public static OutputStorager storager;
    public static InputStorager inputStorager;
    
    @Override
    public void onEnable() {
        this.storager = new OutputStoragerInMemory();
        this.inputStorager = new InputStoragerInMemory();
        instance = this;
        ConfigManager.load();
        mainCfg = new MainConfig();
        try {
            client = new MqttClient("tcp://dev.mineduino.com:1883", MqttClient.generateClientId(), new MemoryPersistence());
            client.connect();
            client.setCallback(new MessageCallback());
        } catch (MqttException ex) {
            Logger.getLogger(MineduinoSpigotPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            client.subscribe("#");
        } catch (MqttException ex) {
            Logger.getLogger(MineduinoSpigotPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TriggerBlocksManager.load();
        instance.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        instance.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        instance.getServer().getPluginManager().registerEvents(new BlockRedstoneListener(), this);
        instance.getServer().getPluginManager().registerEvents(new MQTTCallbackListener(), this);
        instance.getServer().getPluginManager().registerEvents(new SignalEmitListener(), this);
        instance.getServer().getPluginManager().registerEvents(new MQTTPublishListener(), this);
    }

    @Override
    public void onDisable() {
        //maybe necessary?
        try {
            client.disconnect();
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }
    
    public static OutputStorager getStorager() {
        return storager;
    }

    public static InputStorager getInputStorager() {
        return inputStorager;
    }

    public static MqttClient getClient() {
        return client;
    }
    
    
}
