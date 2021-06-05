package com.leilei.anonymous;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2021/6/5 12:29
 * @desc 匿名队列监听
 */
@Component
public class AnonymousConsumer {
    @RabbitListener(queues = "#{testAnonymous.name}")
    public void anonymousListener(String message) throws Exception{
        System.out.println(message);
    }
}
