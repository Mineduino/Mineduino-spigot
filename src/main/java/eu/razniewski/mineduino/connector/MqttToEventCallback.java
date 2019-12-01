package eu.razniewski.mineduino.connector;
import eu.razniewski.mineduino.MineduinoPlugin;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttToEventCallback  implements MqttCallback  {
    MineduinoPlugin plugin = MineduinoPlugin.getInstance();
    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            plugin.getServer().getPluginManager().callEvent(new MineduinoMessageEvent(topic, mqttMessage.getPayload()));
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
