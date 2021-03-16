package com.leilei.confirm;

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
    @RabbitListener(queues = "rabbit_confirm_queue")
    public void aa(Message message, Channel channel) throws Exception{
        try {
            int a = 1 / 0;
            System.out.println("正常收到消息：" + bytesToObject(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            // 两个布尔值  第二个设为 false 则丢弃该消息 设为true 则返回给队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            System.out.println("消费失败，我此次将返回给队列");
            System.out.println("消费失败，我直接丢弃消息，你在队列中想找到我？没门");
        }
    }

    public static <T> Optional<T> bytesToObject(byte[] bytes) {
        T t = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn;
        try {
            sIn = new ObjectInputStream(in);
            t = (T) sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(t);

    }
}
