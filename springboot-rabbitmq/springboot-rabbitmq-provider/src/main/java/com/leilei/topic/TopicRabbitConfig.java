package com.leilei.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author PengLei
 * @date 2020/7/15 0015 9:51
 * @desc topic 主题模式 两个队列 一个topicExchange交换机
 */
@Configuration
public class TopicRabbitConfig {
    /**
     * 队列定义
     * @return
     */
    @Bean
    public Queue topicQueueOne() {
        return new Queue("rabbit_topic_queue_1");
    }
    /**
     * 队列定义
     * @return
     */
    @Bean
    public Queue topicQueueOTwo() {
        return new Queue("rabbit_topic_queue_2");
    }

    /**
     * 定义 TopicExchange 类型交换机
     * @return
     */
    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange("topic_exchange");
    }

    /**
     * 队列一绑定到交换机 且设置路由键为 topic.#
     * @return
     */
    @Bean
    public Binding bindingTopic1() {
        return BindingBuilder.bind(topicQueueOne()).to(exchangeTopic()).with("topic.#");
    }
    /**
     * 队列一绑定到交换机 且设置路由键为 topic.*
     * @return
     */
    @Bean
    public Binding bindingTopic2() {
        return BindingBuilder.bind(topicQueueOTwo()).to(exchangeTopic()).with("topic.*");
    }
}
