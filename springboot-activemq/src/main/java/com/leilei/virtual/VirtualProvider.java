package com.leilei.virtual;
 
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author PengLei
 * @date 2020-07-17 22:00
 * @desc
 */
@Component
@Slf4j
public class VirtualProvider {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private ActiveMQTopic virtualTopicQueue;

    public void sendTopicMessage(String message){
        for (int i = 0; i < 5; i++) {
            jmsMessagingTemplate.convertAndSend(virtualTopicQueue,message);
        }

    }
 
}