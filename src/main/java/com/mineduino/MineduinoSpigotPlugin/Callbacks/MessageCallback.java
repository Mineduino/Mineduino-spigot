/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mineduino.MineduinoSpigotPlugin.Callbacks;

import org.bukkit.Bukkit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author adam
 */
public class MessageCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable thrwbl) {
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Bukkit.getLogger().info(topic + message.toString());
        //Bukkit.getServer().getPluginManager().callEvent(new MQTTCallbackEvent(String string));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        
    }
    
}
