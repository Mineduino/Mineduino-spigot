package eu.razniewski.mineduino.connector;

import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import eu.razniewski.mineduino.MineduinoPlugin;

import java.util.function.Consumer;

public class MqttToEventCallback implements Consumer<Mqtt3Publish> {
    MineduinoPlugin plugin = MineduinoPlugin.getInstance();
    @Override
    public void accept(Mqtt3Publish mqtt3Publish) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            plugin.getServer().getPluginManager().callEvent(new MineduinoMessageEvent(mqtt3Publish.getTopic().toString(), mqtt3Publish.getPayloadAsBytes()));
        });

    }
}
