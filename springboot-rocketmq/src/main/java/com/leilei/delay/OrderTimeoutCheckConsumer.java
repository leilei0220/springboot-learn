package com.leilei.delay;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RocketMQMessageListener(topic = "ORDER_DELAY_TOPIC", consumerGroup = "order-delay-consumer-group")
public class OrderTimeoutCheckConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 这条消息将在发送的 10分钟（级别14）后到达这里
        System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] 收到订单超时检查消息: " + message);
        // 在这里实现你的业务逻辑，例如查询数据库订单是否已支付，未支付则关闭订单
    }
}