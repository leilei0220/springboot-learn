package com.leilei;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @create 2023-06-14 14:49
 * @desc mqtt客户端配置
 **/
@Configuration
public class MqttClientConfig {

    @Value("${mqtt.brokerUrl}")
    private String brokerUrl;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        MqttClient mqttClient = new MqttClient(brokerUrl, clientId);
        mqttClient.connect(options);
        return mqttClient;
    }
}
