package com.leilei.delayed;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author lei
 * @version 1.0
 * @date 2021/9/19 18:38
 * @desc
 */
@Service
public class DelayedConsumer {
    @RabbitListener(queues = "test-delayed-queue")
    public void testListenerDelayedMessage(Message message) {
        byte[] body = message.getBody();
        System.out.println(LocalDateTime.now() + new String(body));
    }
}
