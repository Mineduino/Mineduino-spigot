package com.mineduino.MineduinoSpigotPlugin;

import com.mineduino.MineduinoSpigotPlugin.Callbacks.MessageCallback;
import com.mineduino.MineduinoSpigotPlugin.Listeners.BlockBreakListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.BlockPlaceListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.BlockRedstoneListener;
import com.mineduino.MineduinoSpigotPlugin.Listeners.SignalEmitListener;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MineduinoSpigotPlugin extends JavaPlugin{
	
	public static MineduinoSpigotPlugin instance;
    public static MqttClient client;
	
	@Override
	public void onEnable() {
		instance = this;
		ConfigManager.load();
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
        instance.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        instance.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        instance.getServer().getPluginManager().registerEvents(new BlockRedstoneListener(), this);
        instance.getServer().getPluginManager().registerEvents(new SignalEmitListener(), this);
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
}