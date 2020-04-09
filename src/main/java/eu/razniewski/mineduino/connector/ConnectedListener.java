package eu.razniewski.mineduino.connector;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import eu.razniewski.mineduino.MineduinoPlugin;

public class ConnectedListener implements MqttClientConnectedListener {
    private MqttHandler handler;

    public ConnectedListener(MqttHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onConnected( MqttClientConnectedContext mqttClientConnectedContext) {
        handler.getClient().unsubscribeWith()
                .topicFilter()
                .addLevel("MD")
                .multiLevelWildcard()
                .applyTopicFilter().send().thenAccept((conn) -> {
                    handler.getClient().subscribeWith()
                            .topicFilter()
                            .addLevel("MD")
                            .multiLevelWildcard()
                            .applyTopicFilter()
                            .qos(MqttQos.AT_LEAST_ONCE)
                            .callback(new MqttToEventCallback())
                            .send().exceptionally(throwable -> {
                        MineduinoPlugin.getInstance().getLogger().info("ERROR ERROR ERROR ERROR ERROR");
                        MineduinoPlugin.getInstance().getLogger().info("Can't subscribe to MD/#!");
                        MineduinoPlugin.getInstance().getLogger().info(throwable.getMessage());
                        MineduinoPlugin.getInstance().getLogger().info("ERROR ERROR ERROR ERROR ERROR");
                        return null;
                });
        }).exceptionally(throwable -> {
            return null;
        });


    }
}
