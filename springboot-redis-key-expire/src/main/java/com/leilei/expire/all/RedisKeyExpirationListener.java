// package com.leilei.expire.all;
//
// import lombok.extern.log4j.Log4j2;
// import org.springframework.data.redis.connection.Message;
// import org.springframework.data.redis.connection.MessageListener;
// import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
// import org.springframework.data.redis.listener.RedisMessageListenerContainer;
// import org.springframework.stereotype.Component;
//
// import java.nio.charset.StandardCharsets;
//
// /**
//  * @author lei
//  * @create 2022-09-25 21:42
//  * @desc 监听过期键  (因为KeyExpirationEventMessageListener需要有参RedisMessageListenerContainer构造，
//  * 会导致循环依赖，故此实现更高级抽象接口)
//  **/
// @Component
// @Log4j2
// public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
//
//
//     public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
//         super(listenerContainer);
//     }
//
//     /**
//      * 监控过期键
//      *
//      * @param message 过期的KEY
//      * @param pattern __keyevent@*__:expired
//      * @return void
//      * @author lei
//      * @date 2022-09-25 21:42
//      */
//     @Override
//     public void onMessage(Message message, byte[] pattern) {
//         String expiredKey = message.toString();
//         // 过期消息key:aaa.......pattern:__keyevent@*__:expired
//         log.info("过期消息key:{}.......pattern:{}", expiredKey, new String(pattern, StandardCharsets.UTF_8));
//
//     }
//
// }
