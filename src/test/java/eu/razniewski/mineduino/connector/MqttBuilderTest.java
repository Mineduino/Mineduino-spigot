package eu.razniewski.mineduino.connector;

import com.hivemq.client.mqtt.exceptions.ConnectionFailedException;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3ConnAckException;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class MqttBuilderTest {

    @Test
    void testWssOK() {
        try {
            Mqtt3Client client = MqttBuilder.buildFrom("https://test.mosquitto.org:8081", "", "");
            CompletableFuture<Mqtt3ConnAck> future = client.toAsync().connect();
            Mqtt3ConnAck connection = future.get(70, TimeUnit.SECONDS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail("URI not properly configured, but proper given");
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("It should execute");
        } catch (TimeoutException e) {
            e.printStackTrace();
            fail("Timeout");
        }
    }

    @Test
    void testWssNotExisting() {
        try {
            Mqtt3Client client = MqttBuilder.buildFrom("https://blablalittlechanceforexistenceprobablynochance.com:8888/broker", "admin2", "admin");
            Mqtt3ConnAck future = client.toBlocking().connect();
            fail("Shouldn't connect");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail("URI not properly configured, but proper given");
        } catch (Mqtt3ConnAckException e) {
            e.printStackTrace();
        } catch(ConnectionFailedException e) {
        }
    }

    @Test
    void testTcpFull() {
        try {
            Mqtt3Client client = MqttBuilder.buildFrom("tcp://test.mosquitto.org:1883", "", "");
            CompletableFuture<Mqtt3ConnAck> future = client.toAsync().connect();
            Mqtt3ConnAck connection = future.get(70, TimeUnit.SECONDS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail("URI not properly configured, but proper given");
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("It should execute");
        } catch (TimeoutException e) {
            e.printStackTrace();
            fail("Timeout");
        }
    }
    @Test
    void testTcpNoPortNoSchema() {
        try {
            Mqtt3Client client = MqttBuilder.buildFrom("test.mosquitto.org", "", "");
            CompletableFuture<Mqtt3ConnAck> future = client.toAsync().connect();
            Mqtt3ConnAck connection = future.get(70, TimeUnit.SECONDS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail("URI not properly configured, but proper given");
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("It should execute");
        } catch (TimeoutException e) {
            e.printStackTrace();
            fail("Timeout");
        }
    }
    @Test
    void testTcpNoPortNoPort() {
        try {
            Mqtt3Client client = MqttBuilder.buildFrom("tcp://test.mosquitto.org", "", "");
            CompletableFuture<Mqtt3ConnAck> future = client.toAsync().connect();
            Mqtt3ConnAck connection = future.get(70, TimeUnit.SECONDS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail("URI not properly configured, but proper given");
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("It should execute");
        } catch (TimeoutException e) {
            e.printStackTrace();
            fail("Timeout");
        }
    }

    @Test
    void testWsWithPort() {
        try {
            Mqtt3Client client = MqttBuilder.buildFrom("ws://test.mosquitto.org:8080", "", "");
            CompletableFuture<Mqtt3ConnAck> future = client.toAsync().connect();
            Mqtt3ConnAck connection = future.get(70, TimeUnit.SECONDS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail("URI not properly configured, but proper given");
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("It should execute");
        } catch (TimeoutException e) {
            e.printStackTrace();
            fail("Timeout");
        }

    }
}