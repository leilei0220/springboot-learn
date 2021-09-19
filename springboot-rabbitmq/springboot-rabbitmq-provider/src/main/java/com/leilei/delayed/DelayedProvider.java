package com.leilei.delayed;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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

    public void send(long delayedTime, String messageBody) {
        rabbitTemplate.convertAndSend("test-delayed-exchange", "leilei",messageBody, message -> {
            // 延迟时间单位是毫秒
            message.getMessageProperties().setDelay(Long.valueOf(delayedTime).intValue());
            System.out.println(LocalDateTime.now());
            return message;
        });
    }
}
