package com.leilei.ttl;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 * @version 1.0
 * @desc
 * @date 2021-03-16 16:13
 */
@Configuration
public class TtlRabbitConfig {
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> props = new HashMap<>();
        // 消息存活时间 ,key 固定 value 必须为Int值
        props.put("x-message-ttl", 10000);
        return new Queue("ttl-queue", true, false, false, props);
    }

    @Bean
    public Queue ttlQueue2() {
        // Map<String, Object> props = new HashMap<>();
        // 消息存活时间 ,key 固定 value 必须为Int值,此处不设置，当发小时时候设置
        // props.put("x-message-ttl", 10000);
        return new Queue("ttl-queue-2", true, false, false);
    }

    @Bean
    public FanoutExchange ttlFanoutExchange() {
        return new FanoutExchange("ttl-exchange");
    }
    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlFanoutExchange());
    }

    @Bean
    public Binding ttlBinding2() {
        return BindingBuilder.bind(ttlQueue2()).to(ttlFanoutExchange());
    }
}
