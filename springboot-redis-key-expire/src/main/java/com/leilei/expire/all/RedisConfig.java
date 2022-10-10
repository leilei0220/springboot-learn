// package com.leilei.expire.all;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.listener.PatternTopic;
// import org.springframework.data.redis.listener.RedisMessageListenerContainer;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
// import org.springframework.stereotype.Component;
//
// /**
//  * @author lei
//  * @create 2022-09-25 21:38
//  * @desc redis配置 这里的重点是RedisMessageListenerContainer配置（监听所有DB）
//  **/
// @Component
// public class RedisConfig {
//     private final RedisConnectionFactory redisConnectionFactory;
//
//
//     public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
//         this.redisConnectionFactory = redisConnectionFactory;
//     }
//
//
//     /**
//      * 配置redis模板信息
//      *
//      * @param
//      * @return RedisTemplate<String, Object>
//      * @author lei
//      * @date 2022-09-25 22:11:08
//      */
//     @Bean
//     public RedisTemplate<String, Object> redisTemplate() {
//         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//         redisTemplate.setConnectionFactory(redisConnectionFactory);
//         redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//         StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//         redisTemplate.setKeySerializer(stringRedisSerializer);
//         redisTemplate.setHashKeySerializer(stringRedisSerializer);
//         return redisTemplate;
//     }
//
//
//     /**
//      *
//      * @author lei
//      * @date 2022-09-25 22:06:38
//      * @param connectionFactory
//      * @return RedisMessageListenerContainer
//      */
//     @Bean
//     public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
//         RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//         container.setConnectionFactory(connectionFactory);
//         return container;
//     }
// }
