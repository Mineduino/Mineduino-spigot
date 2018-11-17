package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.mineduino.MineduinoSpigotPlugin.Callbacks.RealToRedstoneEvaluator;
import org.bukkit.event.Cancellable;

public final class MessageArrivedEvent extends Event {

    private int signalStrength;
    private String topic;
    private final static HandlerList handlers = new HandlerList();

    public MessageArrivedEvent(int signalStrength, String topic) {
        this.signalStrength = signalStrength;
        this.topic = topic;
    }

    @Override
    public HandlerList getHandlers() {
	return handlers;
    }
    
    public static HandlerList getHandlerList() {
	return handlers;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public String getTopic() {
        return topic;
    }
    
}
