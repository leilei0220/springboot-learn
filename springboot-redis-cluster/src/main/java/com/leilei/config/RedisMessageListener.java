package com.leilei.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author : leilei
 * @date : 21:52 2020/10/25
 * @desc : redis监听类
 */
@Component
public class RedisMessageListener implements MessageListener {
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //body 即为redis的过期Key了,
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println(LocalDateTime.now()+"redis过期key:"+body);

    }
}