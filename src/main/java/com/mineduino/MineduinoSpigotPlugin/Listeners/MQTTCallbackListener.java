package com.mineduino.MineduinoSpigotPlugin.Listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.mineduino.MineduinoSpigotPlugin.Events.MessageArrivedEvent;
import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class MQTTCallbackListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void mqttCallbackListener(MessageArrivedEvent e) {
		String topic = e.getTopic();
		int signalPower = e.getSignalStrength();
		MineduinoSpigotPlugin.instance.getLogger().info(topic + " " + signalPower);
		Optional<List<Block>> associated = MineduinoSpigotPlugin.getOutputStorager().getAllFromTopic(topic);
                System.out.println(associated);
                if(associated.isPresent()) {
                    for(Block block: associated.get()) {
                        if(signalPower > 0) {
                            Bukkit.getScheduler().runTask(MineduinoSpigotPlugin.instance, () -> {
                                block.setType(Material.REDSTONE_BLOCK);
                            });
                        } else {
                            Bukkit.getScheduler().runTask(MineduinoSpigotPlugin.instance, () -> {
                                block.setType(Material.IRON_BLOCK);
                            });
                            
                        }
                    }
                }
	}
	
}