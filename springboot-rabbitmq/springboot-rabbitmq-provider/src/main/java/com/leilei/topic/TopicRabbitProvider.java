package com.leilei.topic;

import com.alibaba.fastjson.JSON;
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
                rabbitTemplate.convertSendAndReceive("topic_exchange","topic.lei",
                        JSON.toJSONString(new Vehicle(i,i+"单级路由词==车辆")));
            }else {
                rabbitTemplate.convertSendAndReceive("topic_exchange","topic.lei.xxl",
                        JSON.toJSONString(new Vehicle(i,i+"无限级路由词==车辆")));
            }
        }
    }
}
