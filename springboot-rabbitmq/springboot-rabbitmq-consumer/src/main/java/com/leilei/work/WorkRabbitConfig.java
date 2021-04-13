package com.leilei.work;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:30
 * @desc 工作模式 一个生产者 多个消费者
 */
@Configuration
public class WorkRabbitConfig {
    @Bean
    public Queue workQueue() {
        return new Queue("rabbit_work_queue");
    }
}
