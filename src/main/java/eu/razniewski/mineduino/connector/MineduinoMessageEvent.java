package eu.razniewski.mineduino.connector;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MineduinoMessageEvent extends Event {
    private final static HandlerList handlers = new HandlerList();
    private String topic;
    private byte[] message;

    public MineduinoMessageEvent(String topic, byte[] message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
