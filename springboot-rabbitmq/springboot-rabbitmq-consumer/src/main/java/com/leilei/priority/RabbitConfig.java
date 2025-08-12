package com.leilei.priority;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lei
 * @date: 2025-08-12 9:30
 * @desc:
 */
@Configuration
public class RabbitConfig {


    /**
     * x-max-priority 属性设置队列最大优先级
     * @return
     */
    @Bean
    public Queue priorityQueue() {
        return QueueBuilder.durable("priority-queue")
                .withArgument("x-max-priority", 10)
                .build();
    }

    /**
     * RabbitMQ 在处理优先级时：
     * 队列底层是一个优先级排序的堆结构（实际上是多个内部子队列）。
     * 但是 优先级只会影响队列内部的“出队顺序”，不会影响已经被消费者预取(prefetch)到客户端的消息。
     * 如果消费者 prefetch 大于 1（或者 Spring AMQP 默认值），消息一旦被拉到本地内存，就不再按优先级重新排序。
     * @author lei
     * @date 2025-08-12 09:56:34
     * @param connectionFactory
     * @return SimpleRabbitListenerContainerFactory
     */

    @Bean
    public SimpleRabbitListenerContainerFactory customContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 限制一次只取1条，才能更严格保证优先级
        factory.setPrefetchCount(1);
        return factory;
    }

}
