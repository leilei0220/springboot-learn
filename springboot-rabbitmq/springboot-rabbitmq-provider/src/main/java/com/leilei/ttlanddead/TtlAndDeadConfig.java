package com.leilei.ttlanddead;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 * @version 1.0
 * @desc rabbitmq ttl+ 死信
 *   由于是一个队列，遵循先进先出原则，且每次检查只会判断第一个消息是否过期，不会每一个都判断，所以会出现长时间过期的消息会阻塞短时间过期的消息的情况，
 *   也就无法实现同一队列中多种超时时间间隔延时执行。 这种方式也仅适用于过期时间一致的队列。
 * @date 2021-03-16 16:31
 */
@Configuration
public class TtlAndDeadConfig {
    @Bean
    public Queue ttlAndDeadQueue() {
        Map<String, Object> props = new HashMap<>();
        // 消息存活时间 ,key 固定 value 必须为Int值,此种方式则消息存活时间固定，如需动态延迟则将该属性移除
        props.put("x-message-ttl", 10000);
        // 设置该队列所关联的死信交换器（当队列消息TTL到期后依然没有消费，则加入死信队列）
        props.put("x-dead-letter-exchange", "test-dead-exchange");
        // 设置该队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
        props.put("x-dead-letter-routing-key", "dead-key");
        return new Queue("ttl-dead-queue", true, false, false, props);
    }

    @Bean
    public DirectExchange ttlAndDeadExchange() {
        return new DirectExchange("ttl-dead-exchange");
    }
    @Bean
    public Binding ttlAndDeadBinding() {
        return BindingBuilder.bind(ttlAndDeadQueue()).to(ttlAndDeadExchange()).with("test-ttl-and-dead");
    }

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
