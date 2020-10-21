package com.leilei;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * @author lei
 * @version 1.0
 * @date 2020-10-21 17:53
 * @desc
 */
@Configuration
public class ScheduledLockConfig {
    @Bean
    public LockProvider lockProvider(RedisTemplate redisTemplate) {
        return new RedisLockProvider(redisTemplate.getConnectionFactory());
    }
}
