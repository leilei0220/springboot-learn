package com.leilei.ordered;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestOrderedService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 模拟发送一个订单的状态变更流程
     *
     * @param orderId 订单ID (作为 hashKey，保证同一订单的消息发往同一队列)
     */
    public void sendOrderlyMessages(String orderId) {
        // 1. 订单创建消息
        Message<String> createMsg = MessageBuilder.withPayload("订单创建: " + orderId + "创建").build();
        SendResult createResult = rocketMQTemplate.syncSendOrderly(
                "order_topic:STATUS",
                createMsg,
                orderId // 使用订单ID作为hashKey
        );
        System.out.println("发送[创建]消息成功，队列ID: " + createResult.getMessageQueue().getQueueId());

        // 2. 模拟一些业务操作后，发送付款消息
        // ... (你的业务逻辑)
        Message<String> payMsg = MessageBuilder.withPayload("订单付款: " + orderId+ "支付").build();
        SendResult payResult = rocketMQTemplate.syncSendOrderly(
                "order_topic:STATUS",
                payMsg,
                orderId // 必须使用相同的hashKey！
        );
        System.out.println("发送[付款]消息成功，队列ID: " + payResult.getMessageQueue().getQueueId());

        // 3. 最后发送完成消息
        // ... (你的业务逻辑)
        Message<String> finishMsg = MessageBuilder.withPayload("订单完成: " + orderId+ "完成").build();
        SendResult finishResult = rocketMQTemplate.syncSendOrderly(
                "order_topic:STATUS",
                finishMsg,
                orderId // 必须使用相同的hashKey！
        );
        System.out.println("发送[完成]消息成功，队列ID: " + finishResult.getMessageQueue().getQueueId());
    }
}