package com.leilei;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    private final MqttClient mqttClient;

    public MqttController(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @GetMapping("/publish")
    public String publishMessage() {
        String topic = "my/topic";
        String message = "Hello, MQTT!";
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
            return "Message published successfully.";
        } catch (MqttException e) {
            return "Failed to publish message: " + e.getMessage();
        }
    }

    @GetMapping("/subscribe")
    public String subscribeToTopic() {
        String topic = "my/topic/#";

        try {
            mqttClient.subscribe(topic, (curTopic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("curTopic: " + curTopic);
                System.out.println("Received message: " + payload);
                // 处理接收到的消息逻辑
            });
            return "Subscribed to topic: " + topic;
        } catch (MqttException e) {
            return "Failed to subscribe: " + e.getMessage();
        }
    }
}
