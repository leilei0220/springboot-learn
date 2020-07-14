package com.leilei.work;

import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:24
 */
@Component
public class WorkConsumer {
    @RabbitListener(queues = "rabbit_work_queue")
    public void work1(Vehicle vehicle) {
        System.out.println("消费者1--work--接收到车车消息: " + vehicle);
    }
    @RabbitListener(queues = "rabbit_work_queue")
    public void work2(Vehicle vehicle) {
        System.out.println("消费者2--work--接收到车车消息: " + vehicle);
    }
}
