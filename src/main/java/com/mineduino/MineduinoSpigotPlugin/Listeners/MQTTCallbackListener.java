package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.mineduino.MineduinoSpigotPlugin.Events.MQTTCallbackEvent;

public class MQTTCallbackListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void mqttCallbackListener(MQTTCallbackEvent e) {
		String topic = e.getTopic();
		MqttMessage msg = e.getMessage();
	}
	
}