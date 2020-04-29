package eu.razniewski.mineduino.connector;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import eu.razniewski.mineduino.MineduinoPlugin;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MqttBuilder {

    public static Mqtt3AsyncClient buildFrom(String url, String username, String password) throws URISyntaxException {
        if(url.startsWith("tcp://")) {
            return processTcp(url, username, password);
        } else if (url.startsWith("mqtt://")) {
            return processTcp(url, username, password);
        } else if (url.startsWith("ws://")) {
            return processWs(url, username, password);
        } else if (url.startsWith("wss://")) {
            return processWss(url, username, password);
        } else if (url.startsWith("http://")) {
            return processWs(url, username, password);
        } else if (url.startsWith("https://")) {
            return processWss(url, username, password);
        } else {
            if(url.contains("://")) {
                throw new URISyntaxException(url, "Not supported scheme");
            } else {
                return processTcp("tcp://" + url, username, password);
            }
        }
    }

    private static Mqtt3AsyncClient processWs(String replaceFirst, String username, String password) throws URISyntaxException {

        URI uri = new URI(replaceFirst);
        return new MqttBuilderWrapper()
                .setDefaultIdentifier()
                .setFromUri(uri, 80)
                .setAuth(username, password)
                .setDefaultIdentifier()
                .setAutomaticallyReconnect()
                .setForWebsocket(uri.getPath()).buildAsync();
    }

    private static Mqtt3AsyncClient processWss(String replaceFirst, String username, String password) throws URISyntaxException {
        URI uri = new URI(replaceFirst);
        return new MqttBuilderWrapper()
                .setDefaultIdentifier()
                .setFromUri(uri, 443)
                .setAuth(username, password)
                .setDefaultIdentifier()
                .setAutomaticallyReconnect()
                .setForWebsocket(uri.getPath())
                .applySSL()
                .buildAsync();
    }

    private static Mqtt3AsyncClient processTcp(String replaceFirst, String username, String password) throws URISyntaxException {
        URI uri = new URI(replaceFirst);
        return new MqttBuilderWrapper()
                .setDefaultIdentifier()
                .setFromUri(uri, 1883)
                .setAuth(username, password)
                .setDefaultIdentifier()
                .setAutomaticallyReconnect()
                .buildAsync();
    }
}

class DisconnectListener implements MqttClientDisconnectedListener {

    @Override
    public void onDisconnected(MqttClientDisconnectedContext mqttClientDisconnectedContext) {
        if (mqttClientDisconnectedContext.getClientConfig().getState() == MqttClientState.CONNECTING) {
            mqttClientDisconnectedContext.getReconnector().reconnect(false);
        }

    }
}

class MqttBuilderWrapper {
    private Mqtt3ClientBuilder wrapped;
    private URI usedUri;

    public MqttBuilderWrapper() {
        this.wrapped = MqttClient.builder().useMqttVersion3();
    }

    public Mqtt3ClientBuilder getWrapped() {
        return wrapped;
    }

    public MqttBuilderWrapper setDefaultIdentifier() {
        wrapped = wrapped.identifier("MINEDUINO-" + UUID.randomUUID().toString());
        return this;
    }

    public MqttBuilderWrapper setAutomaticallyReconnect() {
        wrapped = wrapped.automaticReconnectWithDefaultConfig()
                .addDisconnectedListener(new DisconnectListener())
                .addConnectedListener(new ConnectedListener(MineduinoPlugin.getMqttHandler()));
        return this;
    }

    public MqttBuilderWrapper setForWebsocket(String path) {
        if(path.startsWith("/")) {
            path = path.replaceFirst("/", "");
        }
        wrapped = wrapped.webSocketConfig().serverPath(path).applyWebSocketConfig();
        return this;
    }

    public MqttBuilderWrapper applySSL() {
        wrapped = wrapped.sslWithDefaultConfig();
        return this;
    }

    public Mqtt3AsyncClient buildAsync() {
        return wrapped.buildAsync();
    }

    public MqttBuilderWrapper setAuth(String username, String password) {
        if(username != null && !username.isEmpty()) {
            if(password != null && !password.isEmpty()) {
                wrapped = wrapped.simpleAuth().username(username).password(password.getBytes(StandardCharsets.UTF_8)).applySimpleAuth();
            } else {
                wrapped = wrapped.simpleAuth().username(username).applySimpleAuth();
            }
        }
        return this;
    }


    public MqttBuilderWrapper setFromUri(URI uri, int defaultPort) {
        if(uri.getPort() > 0) {
            defaultPort = uri.getPort();
        }
        wrapped = wrapped.serverHost(uri.getHost()).serverPort(defaultPort);
        this.usedUri = uri;
        return this;
    }


}