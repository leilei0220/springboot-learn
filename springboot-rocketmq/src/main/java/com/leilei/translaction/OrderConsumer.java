package com.leilei.translaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    topic = "order_topic",
    consumerGroup = "tx_consumer_group",selectorExpression = "tagA"
)
public class OrderConsumer implements RocketMQListener<OrderMessage> {

    @Override
    public void onMessage(OrderMessage order) {
        System.out.println("\n=================================");
        System.out.println("📦 收到订单消息: " + order.getOrderId());
        System.out.println("💳 事务ID: " + order.getTransactionId());
        System.out.println("💰 金额: " + order.getAmount());
        System.out.println("✅ 订单处理完成");
        System.out.println("=================================\n");
    }
}