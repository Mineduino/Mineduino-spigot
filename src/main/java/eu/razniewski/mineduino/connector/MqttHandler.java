package eu.razniewski.mineduino.connector;

import com.google.common.primitives.Ints;
import eu.razniewski.mineduino.MineduinoPlugin;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.Charset;

public class MqttHandler {
    private MqttAsyncClient client;

    public boolean connectTo(String uri) {
        try {
            client = new MqttAsyncClient(uri, MqttAsyncClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(true);
            client.connect(opts, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    try {
                        client.subscribe("MD/#", 0);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    client.setCallback(new MqttToEventCallback());
                    MineduinoPlugin.getInstance().getLogger().info("FULLY CONNECTED TO MINEDUINO!");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    MineduinoPlugin.getInstance().getLogger().info("FAILED TO CONNECT TO MINEDUINO!");
                }
            });
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;

    }
    public MqttAsyncClient getClient() {
        return client;
    }

    public boolean isConnected() {
        return client != null && client.isConnected();
    }
    public boolean standardPublish(String topic, String message) {
        if(this.client == null) {
            throw new IllegalStateException("First of all connect to broker");
        }
        try {

            MqttMessage mqttMessage = new MqttMessage(message.getBytes(Charset.forName("UTF-8")));
            mqttMessage.setRetained(true);
            this.client.publish(topic, mqttMessage);
            return true;
        } catch (MqttException e) {
            return false;
        }
    }
    public boolean standardPublish(String topic, byte value) {
        if(this.client == null) {
            throw new IllegalStateException("First of all connect to broker");
        }
        try {
            MqttMessage message = new MqttMessage(new byte[]{value});
            message.setRetained(true);
            this.client.publish(topic, message);
            return true;
        } catch (MqttException e) {
            return false;
        }
    }



}
