package com.leilei.delayed;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 * @version 1.0
 * @date 2021/9/19 18:25
 * @desc rabbitMq延迟消息配置示例 （rabbitmq需安装 延迟消息插件  http://www.rabbitmq.com/community-plugins.html）
 */
@Configuration
public class RabbitMqDelayedConfig {


    @Bean("delayExchange")
    public Exchange delayExchange() {
        Map<String, Object> args = new HashMap<>(2);
        //  x-delayed-type    声明 延迟队列Exchange的类型
        args.put("x-delayed-type", "direct");
        // 设置名字 交换机类型 持久化 不自动删除 
        return new CustomExchange("test-delayed-exchange", "x-delayed-message", true, false, args);
    }

    @Bean("delayQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable("test-delayed-queue").build();
    }

    /**
     * 将延迟队列通过routingKey绑定到延迟交换器
     *
     * @return
     */
    @Bean
    public Binding delayQueueBindExchange(Exchange delayExchange, Queue delayQueue) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with("leilei").noargs();
    }

}
