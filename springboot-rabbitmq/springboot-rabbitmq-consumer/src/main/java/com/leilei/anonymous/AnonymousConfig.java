package com.leilei.anonymous;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @version 1.0
 * @date 2021/6/5 12:16
 * @desc rabbitMQ匿名队列,此场景作用与fanout广播时，但多个consumer实例均要收到消息的场景
 */
@Configuration
public class AnonymousConfig {
    /**
     * 匿名队列 （默认不会持久化，项目重启会删除队列并生成一个全新的queue name）
     * 我这里采用了uuid生成队列名，开发时可添加一个项目前缀
     * @return
     */
    @Bean
    public AnonymousQueue testAnonymous() {
        return new AnonymousQueue(UUIDNamingStrategy.DEFAULT);
    }

    /**
     * 普通的fanout交换机
     * @return
     */
    @Bean
    public FanoutExchange myFanoutExchange() {
        return new FanoutExchange("lei_fanout_exchange");
    }

    /**
     * 匿名队列绑定到交换机
     * @return
     */
    @Bean
    public Binding anonymousBind() {
        return BindingBuilder.bind(testAnonymous()).to(myFanoutExchange());
    }
}
