package com.leilei.easy;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:20
 * @desc 最简单模式 一个生产者一个消费者
 */
@Configuration
public class EasyRabbitConfig {
    @Bean
    public Queue easyQueue() {
        return new Queue("rabbit_easy_queue");
    }
}
