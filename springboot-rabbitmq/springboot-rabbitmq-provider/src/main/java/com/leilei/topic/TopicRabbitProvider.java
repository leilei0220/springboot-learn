package com.leilei.topic;

import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PengLei
 * @date 2020/7/15 0015 09:55
 * @desc
 */
@Service
public class TopicRabbitProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTopMessage() {
        for (int i = 0; i < 10; i++) {
            if (i%2==0) {
                rabbitTemplate.convertSendAndReceive("topic_exchange","topic.lei",new Vehicle(i,i+"一个词路由键车车"));
            }else {
                rabbitTemplate.convertSendAndReceive("topic_exchange","topic.lei.xxl",new Vehicle(i,i+"多个词路由键车车"));
            }
        }
    }
}
