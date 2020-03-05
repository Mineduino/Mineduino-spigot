package eu.razniewski.mineduino.connector;

import com.google.common.primitives.Ints;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.config.CachedJsonFileConfigManager;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import eu.razniewski.mineduino.config.CachedJsonFileConfigManager;
import eu.razniewski.mineduino.config.ConfigManager;
import java.nio.charset.Charset;

public class MqttHandler {
    ConfigManager manager;
    private MqttAsyncClient client;
    public boolean connectTo(String uri) {
        try {
            manager = new CachedJsonFileConfigManager("plugins/mineduino/config.json");
            client = new MqttAsyncClient(uri, MqttAsyncClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(true);
            opts.setUserName(manager.<String>getValue("broker_login_username").get());
            opts.setPassword(manager.<String>getValue("broker_login_password").get().toCharArray());
            client.connect(opts, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    try {
                        client.subscribe("MD/#", 0);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    client.setCallback(new MqttToEventCallback());
                    manager = new CachedJsonFileConfigManager("plugins/mineduino/config.json");
                    MineduinoPlugin.getInstance().getLogger().info("___________________________________________");
                    MineduinoPlugin.getInstance().getLogger().info("Successfully connected to MQTT broker!");
                    MineduinoPlugin.getInstance().getLogger().info("Broker: " + manager.<String>getValue("broker").get());
                    MineduinoPlugin.getInstance().getLogger().info("___________________________________________");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    manager = new CachedJsonFileConfigManager("plugins/mineduino/config.json");
                    MineduinoPlugin.getInstance().getLogger().info("___________________________________________");
                    MineduinoPlugin.getInstance().getLogger().info("Unable to reach MQTT broker!");
                    MineduinoPlugin.getInstance().getLogger().info("Broker: " + manager.<String>getValue("broker").get());
                    MineduinoPlugin.getInstance().getLogger().info("___________________________________________");
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
