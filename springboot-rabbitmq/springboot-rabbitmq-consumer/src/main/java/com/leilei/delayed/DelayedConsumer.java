package com.leilei.delayed;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        System.out.println("消费者接受到消息,接收时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")) +
                " 消息内容: " + new String(body));
    }
}
