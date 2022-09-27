package com.leilei.expire.db2;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author lei
 * @create 2022-09-27 10:12
 * @desc redis 过期KEY监听
 **/
@Component
@Log4j2
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, new PatternTopic("__keyevent@11__:expired"));
    }

    /**
     * 坚挺到过期消息
     *
     * @param message key
     * @param pattern 消息事件
     * @return void
     * @author lei
     * @date 2022-09-27 10:17:54
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        // 过期消息key:aaa.......pattern:__keyevent@11__:expired
        log.info("过期消息key:{}.......pattern:{}", expiredKey, new String(pattern, StandardCharsets.UTF_8));
    }
}
