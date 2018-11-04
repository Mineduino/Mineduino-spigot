package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public final class MQTTCallbackEvent extends Event{
	
	private String topic;
	private MqttMessage msg;
	
	public MQTTCallbackEvent(String topic, MqttMessage msg) {
		this.topic = topic;
		this.msg = msg;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public MqttMessage getMessage() {
		return this.msg;
	}

}
