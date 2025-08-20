package com.leilei.delay;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: lei
 * @date: 2025-08-20 11:16
 * @desc: 测试延迟消息
 */
@Service
public class DelayMessageService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送一个延迟消息：10分钟后检查订单是否支付超时
     */
    public void sendDelayedMessage() {
        // 1. 构建业务数据（Payload）
        String orderId = "ORDER_20231027_001";
        String messagePayload = "订单ID: " + orderId+":发送时间"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));

        // 2. 构建 Spring Message 对象
        //    可以设置一些 headers，但对于延迟消息，关键是后面的 delayLevel 参数
        org.springframework.messaging.Message<String> message = MessageBuilder
                .withPayload(messagePayload)
                // 设置一个自定义头，方便消费者识别
                .setHeader("orderId", orderId)
                .build();

        // 3. 发送延迟消息
        //    参数1: destination (Topic:Tag)
        //    参数2: 消息对象
        //    参数3: 发送超时时间（毫秒）
        //    参数4: 延迟级别 (4 对应 30秒, 5 对应 1分钟, ... 14 对应 10分钟)
        // 本例使用级别 14，即 10分钟延迟
        SendResult result = rocketMQTemplate.syncSend(
                "ORDER_DELAY_TOPIC:timeout_check",
                message,
                // 发送操作超时时间为3秒
                3000,
                // 延迟级别为 4 (30s)
                4
        );
        // rocketMQTemplate.asyncSend(
        //         "YOUR_TOPIC:YOUR_TAG",
        //         message,
        //         new SendCallback() {
        //             @Override
        //             public void onSuccess(SendResult sendResult) {
        //                 System.out.println("异步延迟消息发送成功! " + sendResult);
        //             }
        //
        //             @Override
        //             public void onException(Throwable e) {
        //                 System.err.println("异步延迟消息发送失败! ");
        //                 e.printStackTrace();
        //                 // 这里必须要有重试或告警机制！
        //             }
        //         },
        //         // 发送超时时间
        //         3000,
        //         // 延迟级别 (3 -> 10秒)
        //         3
        // );

        System.out.println("延迟消息发送成功！MsgId: " + result.getMsgId());
    }
}
