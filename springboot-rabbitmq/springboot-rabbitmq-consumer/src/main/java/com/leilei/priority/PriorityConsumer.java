package com.leilei.priority;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PriorityConsumer {


    @RabbitListener(queues = "priority-queue",containerFactory = "customContainerFactory")
    public void receive(String message) {
        System.out.println("消费者接收消息: " + message);
    }

    // @RabbitListener(queues = "priority-queue")
    // public void receive(String message) {
    //     System.out.println("消费者接收消息: " + message);
    // }
}
