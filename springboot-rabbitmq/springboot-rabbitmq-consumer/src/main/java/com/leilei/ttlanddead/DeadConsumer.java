package com.leilei.ttlanddead;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lei
 * @version 1.0
 * @desc
 * @date 2021-03-16 16:37
 */
@Component
public class DeadConsumer {
    @RabbitListener(queues = "dead-queue")
    public void listenOne(Message message) {
        System.out.println("当前时间:"+ LocalDateTime.now() +",死信队列收到消息：" + JSON.parseObject(new String(message.getBody()), Vehicle.class));
    }
}
