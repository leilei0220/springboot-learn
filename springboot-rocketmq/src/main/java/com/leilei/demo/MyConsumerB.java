package com.leilei.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(
        topic = "TestTopic",
        consumerGroup = "demo-consumer-group1",
        messageModel = MessageModel.CLUSTERING // 集群模式，改成 BROADCASTING 就是广播模式
)
public class MyConsumerB implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("收到消息B: {}", message);
    }
}
