package com.leilei.confirm;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/15 20:24
 * @desc 消息发送确认机制 消费者demo
 */
@Component
public class ConfirmConsumer {
    private static int a = 0;
    @RabbitListener(queues = "rabbit_confirm_queue")
    public void aa(Message message, Channel channel) throws Exception{
        try {
            // if (a < 4) {
            // int cc = 1 / 0;
            // }
            System.out.println("正常收到消息：" + JSON.parseObject(new String(message.getBody()), Vehicle.class));
            // 布尔值 false 告诉MQ删除这一条消息，若是true，则是删除所有小于tags的消息。
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            // 两个布尔值 第一个布尔值false告诉MQ消息补发到当前消费者，。第二个设为 false 则丢弃该消息 设为true 则返回给队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
           System.out.println("消费失败，我此次将返回给队列");
            a++;
//             System.out.println("消费失败，我直接丢弃消息，你在队列中想找到我？没门");
        }
    }

}
