package com.leilei.priority;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PriorityProducer {

    private final RabbitTemplate rabbitTemplate;

    public PriorityProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String body, int priority) {
        MessageProperties props = new MessageProperties();
        // 设置消息优先级
        props.setPriority(priority);
        Message message = new Message(body.getBytes(), props);

        rabbitTemplate.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
        System.out.println("发送消息: " + body + " , 优先级: " + priority);
    }
}
