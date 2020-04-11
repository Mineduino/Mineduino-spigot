package eu.razniewski.mineduino.connector;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.config.CachedJsonFileConfigManager;
import eu.razniewski.mineduino.config.ConfigManager;

import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class MqttHandler {
    ConfigManager manager;
    private Mqtt3AsyncClient client;

    public boolean connectTo(String uri) {
        manager = new CachedJsonFileConfigManager("plugins/mineduino/config.json");
        String username = manager.<String>getValue("broker_login_username").get();
        String password = manager.<String>getValue("broker_login_password").get();
        try {
            client = MqttBuilder.buildFrom(manager.<String>getValue("broker").get(), username, password);
        } catch (URISyntaxException e) {
            return false;
        }
        client.connectWith().keepAlive(30).send().thenAccept((connAck) -> {
            MineduinoPlugin.getInstance().getLogger().info("___________________________________________");
            MineduinoPlugin.getInstance().getLogger().info("Successfully connected to MQTT broker!");
            MineduinoPlugin.getInstance().getLogger().info("Broker: " + manager.<String>getValue("broker").get());
            MineduinoPlugin.getInstance().getLogger().info("___________________________________________");
        }).exceptionally(throwable -> {
            MineduinoPlugin.getInstance().getLogger().info("ERROR ERROR ERROR ERROR ERROR");
            MineduinoPlugin.getInstance().getLogger().info("Can't connect to the broker :(!");
            MineduinoPlugin.getInstance().getLogger().info(throwable.getMessage());
            MineduinoPlugin.getInstance().getLogger().info("ERROR ERROR ERROR ERROR ERROR");
            return null;
        });
        return true;

    }

    public Mqtt3AsyncClient getClient() {
        return client;
    }

    public boolean standardPublish(String topic, String message) {
        if (this.client == null) {
            throw new IllegalStateException("First of all connect to broker");
        }
        client.publishWith().topic(topic).retain(true).payload(message.getBytes(Charset.forName("UTF-8"))).send();
        return true;
    }

    public boolean standardPublish(String topic, byte value) {
        if (this.client == null) {
            throw new IllegalStateException("First of all connect to broker");
        }
        client.publishWith().topic(topic).retain(true).payload(new byte[]{value}).send();
        return true;
    }


}
