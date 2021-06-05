package com.leilei.anonymous;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @version 1.0
 * @date 2021/6/5 12:16
 * @desc rabbitMQ匿名队列,此场景作用与fanout广播时，但多个consumer实例均要收到消息的场景
 */
@Configuration
public class RabbitFanoutConfig {
    @Bean
    public FanoutExchange myFanoutExchange() {
        return new FanoutExchange("lei_fanout_exchange");
    }
}
