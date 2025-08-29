package com.leilei.ordered;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
// 关键配置：consumeMode = ConsumeMode.ORDERLY
@RocketMQMessageListener(
        topic = "order_topic",
        selectorExpression = "STATUS", // 消费Tag为STATUS的消息
        consumerGroup = "order-status-consumer-group",
        consumeMode = ConsumeMode.ORDERLY // 设置为顺序消费模式
)
public class OrderStatusConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 对于同一个队列的消息，RocketMQ会加锁，顺序地、单线程地调用此方法
        System.out.println(Thread.currentThread().getName() + " 收到消息: " + message);

        // 模拟业务处理
        try {
            // 顺序业务逻辑在这里处理
            Thread.sleep(new Random().nextInt(5000) + 1); // 模拟处理耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}