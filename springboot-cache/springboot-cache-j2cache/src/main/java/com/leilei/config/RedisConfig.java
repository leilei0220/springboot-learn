package com.leilei.config;

import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 18:12
 * @desc redis配置， 配合spring-cache注解，将数据缓存到redis
 */
@Configuration
public class RedisConfig {

    @Value("${j2cache.config-location}")
    private String properties;

    @Bean
    @Primary
    public CacheManager cacheManager(){
        J2CacheConfig config = null;
        try {
            config = J2CacheConfig.initFromConfig(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        J2CacheBuilder j2CacheBuilder = J2CacheBuilder.init(config);
        return new J2CacheSpringCacheManageAdapter(j2CacheBuilder, true);
    }

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     *
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        // 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new FastJson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer<>(Object.class));
        // 开启事务 需注意 redis 事务仅仅对单机有效，集群目前是不支持事务的
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }



}
