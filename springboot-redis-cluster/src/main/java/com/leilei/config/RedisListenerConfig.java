//package com.leilei.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//
///**
// * @author lei
// * @version 1.0
// * @date 2020-10-23 11:31
// * @desc
// */
//@Configuration
//public class RedisListenerConfig {
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        return container;
//    }
//}
