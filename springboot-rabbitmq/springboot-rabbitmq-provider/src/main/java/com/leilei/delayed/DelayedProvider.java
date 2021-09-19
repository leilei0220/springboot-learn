package com.leilei.delayed;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lei
 * @version 1.0
 * @date 2021/9/19 18:33
 * @desc
 */
@Service
public class DelayedProvider {
    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public DelayedProvider(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(int delayedTime, String messageBody) {
        // 指定之前定义的延迟交换机名 与路由键名
        rabbitTemplate.convertAndSend("test-delayed-exchange", "leilei", messageBody, message -> {
            // 延迟时间单位是毫秒
            message.getMessageProperties().setDelay(delayedTime);
            System.out.println("消息发送时间:" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")) +
                    " 消息内容: " + messageBody);
            return message;
        });
    }
}
