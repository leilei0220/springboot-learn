package com.leilei.topic;

import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author PengLei
 * @date 2020/7/15 0015 10:00
 * @desc
 */
@Component
public class TopRabbitConsumer {
    @RabbitListener(queues = "rabbit_topic_queue_1")
    public void listenOne(Vehicle vehicle) {
        System.out.println("监听到队列一消息" + vehicle);
    }
    @RabbitListener(queues = "rabbit_topic_queue_2")
    public void listenOTwo(Vehicle vehicle) {
        System.out.println("监听到队列二消息" + vehicle);
    }
}
