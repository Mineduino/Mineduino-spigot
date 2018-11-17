/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.mineduino.MineduinoSpigotPlugin.MineduinoSpigotPlugin;
import com.mineduino.MineduinoSpigotPlugin.Events.MessageArrivedEvent;

/**
 *
 * @author adam
 */
public class MessageCallback implements MqttCallback {
	
    MineduinoSpigotPlugin plugin = MineduinoSpigotPlugin.instance;
    private Gson gson = new Gson();

    private Recognizer recognizer = new MessageRecognizer();
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        plugin.getLogger().info(topic + message.toString());
        int signalStrength = recognizer.recognizeAndEvaluate(message.toString());
        plugin.getServer().getPluginManager().callEvent(new MessageArrivedEvent(signalStrength, topic));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        
    }
    
}
