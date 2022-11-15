package com.leilei.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @create 2022-09-19 21:44
 * @desc mq配置
 **/
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue bizQueue() {
        return new Queue("bizQueue");
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("replyQueue");
    }

    @Bean
    public FanoutExchange bizExchange() {
        return new FanoutExchange("bizExchange");
    }

    @Bean
    public Binding bizBind(Queue bizQueue, FanoutExchange bizExchange) {
        return BindingBuilder.bind(bizQueue).to(bizExchange);
    }
}
