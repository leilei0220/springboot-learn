package com.leilei.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 18:12
 * @desc redis配置， 配合spring-cache注解，将数据缓存到redis
 */
@Configuration
public class RedisConfig {
    private final RedisConnectionFactory connectionFactory;
    @Autowired
    public RedisConfig(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    @Bean
    @Primary
    public RedisCacheManager cacheManager() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper mp = new ObjectMapper();
        // 防止在序列化的过程中丢失对象的属性
        mp.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 开启实体类和json的类型转换
        mp.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mp);

        // 配置序列化，缓存过期时间等
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //key的序列化方式 String
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                //value的序列化方式 使用fastJson
                .serializeValuesWith(RedisSerializationContext.
                        SerializationPair
                        .fromSerializer(new FastJson2JsonRedisSerializer<>(Object.class)))
                .disableCachingNullValues();
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
    }
}
