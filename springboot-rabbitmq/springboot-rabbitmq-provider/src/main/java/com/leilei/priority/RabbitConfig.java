package com.leilei.priority;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 优先级队列（Priority Queue）是指消费者会优先处理高优先级的消息。
 *
 * 每条消息可以带一个 priority 属性（整数，0~最大值之间）。
 * RabbitMQ 在投递消息到消费者前，会根据 priority 排序，高优先级的消息会先被消费。
 * 如果多个消息优先级相同，则按 FIFO（先进先出）顺序消费。

 * 2. 如何实现
 * RabbitMQ 没有为所有队列默认开启优先级功能，需要在声明队列时设置参数：
 * x-max-priority：最大优先级值（一般建议 0~255 之间）。
 * 消息发送时设置 priority 属性，RabbitMQ 会将消息放入优先级队列中。

 * 3. 注意事项
 * 优先级队列会消耗更多内存和 CPU，因为需要维护一个优先级排序结构。
 * 多优先级（比如 255）不一定带来更好效果，一般 5~10 就够。
 * 如果消息没设置优先级，则使用默认 0。
 */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "priority-exchange";
    public static final String QUEUE_NAME = "priority-queue";
    public static final String ROUTING_KEY = "priority-key";

    @Bean
    public DirectExchange priorityExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue priorityQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-max-priority", 10) // 最大优先级为10
                .build();
    }

    @Bean
    public Binding bindingPriorityQueue() {
        return BindingBuilder.bind(priorityQueue())
                .to(priorityExchange())
                .with(ROUTING_KEY);
    }
}
