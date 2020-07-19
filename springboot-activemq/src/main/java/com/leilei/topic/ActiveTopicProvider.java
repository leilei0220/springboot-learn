package com.leilei.topic;

import com.leilei.common.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author PengLei
 * @date 2020-07-17 13:21
 * @desc
 */
@Service
public class ActiveTopicProvider {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage() {
        for (int i = 0; i < 30; i++) {
        jmsMessagingTemplate.convertAndSend("lei_topic",new Driver((long)i,"司机"+i));
        }
    }
}
