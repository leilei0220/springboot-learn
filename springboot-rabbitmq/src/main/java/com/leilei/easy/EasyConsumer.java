package com.leilei.easy;

import com.leilei.common.Vehicle;
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
    @RabbitHandler
    public void process(Vehicle vehicle) {
        System.out.println("简单消费者接收到车车消息: " + vehicle);
    }
}
