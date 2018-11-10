package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public final class MQTTCallbackEvent extends Event{
	
	private String topic;
	private MqttMessage msg;
	
	private final HandlerList handlers = new HandlerList();
	
	public MQTTCallbackEvent(String topic, MqttMessage msg) {
		this.topic = topic;
		this.msg = msg;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public MqttMessage getMessage() {
		return this.msg;
	}

}
