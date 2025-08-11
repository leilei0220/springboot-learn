package com.leilei.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
/**
 * spring:
 *   rabbitmq:
 *     # 开启消息确认机制 confirm 异步
 *     publisher-confirm-type: correlated
 *     publisher-returns: true
 *     template:
 *       mandatory: true
 * @author lei
 * @date 2025-08-11 17:52:22
 */
@Configuration
@Slf4j
public class RabbitConfig {
    @Bean
    public RabbitTemplate rabbitTemplateConfirm(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("confirm回调，线程:{} 确认: {}, data={}", Thread.currentThread().getName(), ack, correlationData);
        });
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("return回调，线程:{} 丢失消息:{}", Thread.currentThread().getName(), message);
        });
        template.setMandatory(true);
        return template;
    }


    @Bean
    public DirectExchange testExchange() {
        return new DirectExchange("test-exchange");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test-queue");
    }

    @Bean
    public Binding binding(Queue testQueue, DirectExchange testExchange) {
        return BindingBuilder.bind(testQueue).to(testExchange).with("rk");
    }


}
