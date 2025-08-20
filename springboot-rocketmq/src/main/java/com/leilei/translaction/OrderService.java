package com.leilei.translaction;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    public OrderService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void createOrder() {
        String transactionId = UUID.randomUUID().toString();
        OrderMessage order = new OrderMessage(
                transactionId,
                "ORD-" + System.currentTimeMillis(),
                100.0
        );

        // 构建消息
        Message<OrderMessage> message = MessageBuilder.withPayload(order)
                .setHeader("tx_id", transactionId)
                .setHeader("businessType", "order")
                .build();

        System.out.println("🚀 发送事务消息: " + transactionId);
        // 2.3.3 版本使用 sendMessageInTransaction 方法
        rocketMQTemplate.sendMessageInTransaction(
                "order_topic" + ":" + "tagA",
                message,
                null
        );
    }
}