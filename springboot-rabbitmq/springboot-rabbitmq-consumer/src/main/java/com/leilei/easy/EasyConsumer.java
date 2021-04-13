package com.leilei.easy;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:24
 */
@Component
public class EasyConsumer {
    @RabbitListener(queues = "rabbit_easy_queue")
    public void process(Message message) {
        System.out.println("easy模式：消费者接收到车辆消息: " + JSON.parseObject(new String(message.getBody()), Vehicle.class));
    }
}
