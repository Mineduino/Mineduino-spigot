package com.mineduino.MineduinoSpigotPlugin.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.mineduino.MineduinoSpigotPlugin.Callbacks.RealToRedstoneEvaluator;
import org.bukkit.event.Cancellable;

public final class MessageArrivedEvent extends Event {

    private RealToRedstoneEvaluator evaluator;
    private String topic;
    private final static HandlerList handlers = new HandlerList();

    public MessageArrivedEvent(RealToRedstoneEvaluator evaluator, String topic) {
        this.evaluator = evaluator;
        this.topic = topic;
    }

    @Override
    public HandlerList getHandlers() {
	return handlers;
    }
    
    public static HandlerList getHandlerList() {
	return handlers;
    }

    public RealToRedstoneEvaluator getEvaluator() {
        return evaluator;
    }

    public String getTopic() {
        return topic;
    }
    
}
