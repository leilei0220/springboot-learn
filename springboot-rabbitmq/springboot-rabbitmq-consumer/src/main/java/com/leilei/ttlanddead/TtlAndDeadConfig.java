package com.leilei.ttlanddead;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 * @version 1.0
 * @desc rabbitmq ttl+ 死信
 * @date 2021-03-16 16:31
 */
@Configuration
public class TtlAndDeadConfig {

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return new Queue("dead-queue", true, false, false);
    }

    /**
     * 死信交换器
     * @return
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("test-dead-exchange", true, false, null);
    }

    /**
     * 死信队列绑定死信交换机
     * @return
     */
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead-key");
    }


}
