package com.leilei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
/**
 * @author : leilei
 * @date : 21:54 2020/10/25
 * @desc : redis监听配置类
 */
@Configuration
public class RedisMessageConfig {

    private final RedisMessageListener redisMessageListener;
    @Autowired
    public RedisMessageConfig(RedisMessageListener redisMessageListener) {
        this.redisMessageListener = redisMessageListener;
    }
    /**
     * 监听策略..
     * __keyevent@db__:expired
     * __keyevent@1__:expired    即监听索引库1 的过期key
     * __keyevent@2__:expired    即监听索引库2 的过期key
     * __keyevent@*__:expired    即监听所有索引库 的过期key
     * @return
     */
    @Bean
    public PatternTopic topic() {
        return new PatternTopic("__keyevent@*__:expired");
    }
 
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            @Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        //将监听类，以及监听策略作为参数
        redisMessageListenerContainer.addMessageListener(redisMessageListener,topic());
        return redisMessageListenerContainer;
    }
}
 