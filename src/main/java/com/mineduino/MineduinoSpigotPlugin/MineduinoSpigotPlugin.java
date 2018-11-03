package com.mineduino.MineduinoSpigotPlugin;

import com.mineduino.MineduinoSpigotPlugin.Callbacks.MessageCallback;
import org.bukkit.plugin.java.JavaPlugin;

import com.mineduino.MineduinoSpigotPlugin.Handlers.SignalHandler;
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
		
		instance.getServer().getPluginManager().registerEvents(new SignalHandler(), instance);
            try {
                client = new MqttClient("tcp://dev.mineduino.com:1883", MqttClient.generateClientId(), new MemoryPersistence());
                client.connect();
                client.setCallback(new MessageCallback());
            } catch (MqttException ex) {
                Logger.getLogger(MineduinoSpigotPlugin.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                client.subscribe("test");
            } catch (MqttException ex) {
                Logger.getLogger(MineduinoSpigotPlugin.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
		
	}
	//TEST
	@Override
	public void onDisable() {
		
	}
}