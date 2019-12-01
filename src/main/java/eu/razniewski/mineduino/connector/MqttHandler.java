package eu.razniewski.mineduino.connector;

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
                        client.subscribe("MD/+/i/+", 0);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    client.setCallback(new MqttToEventCallback());
                    System.out.println("FULLY CONNECTED TO MINEDUINO");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    System.out.println("FAILED TO CONNECT TO MINEDUINO");
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
            this.client.publish(topic, new MqttMessage(message.getBytes(Charset.forName("UTF-8"))));
            return true;
        } catch (MqttException e) {
            return false;
        }
    }



}
