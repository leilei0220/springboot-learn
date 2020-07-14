package com.leilei.fanoutexchange;

import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 22:14
 * @desc 发布订阅消费者   一个对了绑定两个消费者
 */
@Component
public class FantoutExchangeConsumer {
    @RabbitListener(queues = "rabbit_fanout_queue_one")
    public void consumerOne(Vehicle vehicle) {
        System.out.println("rabbit_fanout_queue_one队列 消费者1：收到消息---" + vehicle);
    }
    @RabbitListener(queues = "rabbit_fanout_queue_one")
    public void consumerOne2(Vehicle vehicle) {
        System.out.println("rabbit_fanout_queue_one队列 消费者2：收到消息---" + vehicle);
    }
    //-------------一个队列绑定两个消费者 --------------------------------
    @RabbitListener(queues = "rabbit_fanout_queue_two")
    public void consumerTwo(Vehicle vehicle) {
        System.out.println("rabbit_fanout_queue_two队列 消费者1：收到消息---" + vehicle);
    }
    @RabbitListener(queues = "rabbit_fanout_queue_two")
    public void consumerTwo2(Vehicle vehicle) {
        System.out.println("rabbit_fanout_queue_two队列 消费者2：收到消息---" + vehicle);
    }
}
