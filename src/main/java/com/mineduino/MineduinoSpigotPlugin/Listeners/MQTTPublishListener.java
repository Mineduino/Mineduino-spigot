/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Listeners;

import com.google.gson.Gson;
import com.mineduino.MineduinoSpigotPlugin.Events.PublishEvent;
import com.mineduino.MineduinoSpigotPlugin.Events.SendObject;
import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author adam
 */
public class MQTTPublishListener implements Listener{
    Gson gson = new Gson();
    @EventHandler(priority = EventPriority.HIGHEST)
    public void publishListener(PublishEvent e) {
        
        try {
            MineduinoSpigotPlugin.getClient().publish(e.getTopic(), new MqttMessage(gson.toJson(new SendObject(e.isValue())).getBytes()));
        } catch (MqttException ex) {
            Logger.getLogger(MQTTPublishListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
