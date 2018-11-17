package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.mineduino.MineduinoSpigotPlugin.Events.MessageArrivedEvent;
import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import com.mineduino.MineduinoSpigotPlugin.TriggerBlocks.OutputTriggerBlock;
import java.util.List;
import java.util.Optional;

public class MQTTCallbackListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void mqttCallbackListener(MessageArrivedEvent e) {
		String topic = e.getTopic();
                int signalPower = e.getEvaluator().getRedstoneSignalPower();
                Optional<List<OutputTriggerBlock>> associated = MineduinoSpigotPlugin.getStorager().getAllFromTopic(topic);
                if(associated.isPresent()) {
                    for(OutputTriggerBlock block: associated.get()) {
                        //@TODO - trigger signal with block and signalPower
                    }
                }
	}
	
}