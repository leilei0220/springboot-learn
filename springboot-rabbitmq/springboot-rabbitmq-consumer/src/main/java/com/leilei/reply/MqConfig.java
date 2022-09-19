package com.leilei.reply;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @create 2022-09-19 15:09
 * @desc reply测试 mq配置
 **/
@Configuration
public class MqConfig {

    @Bean
    public Queue bizQueue() {
        return new Queue("bizQueue");
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("replyQueue");
    }

    @Bean
    FanoutExchange bizExchange() {
        return new FanoutExchange("bizExchange");
    }


    @Bean
    Binding bizBind() {
        return BindingBuilder.bind(bizQueue()).to(bizExchange());
    }

}
