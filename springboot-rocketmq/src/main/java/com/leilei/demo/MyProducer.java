package com.leilei.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String msg) {
        log.info("发送消息: topic={}, body={}", topic, msg);
        rocketMQTemplate.syncSend(topic, msg);
    }
}
