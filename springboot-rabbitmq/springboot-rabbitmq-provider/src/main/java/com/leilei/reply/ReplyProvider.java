package com.leilei.reply;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author lei
 * @create 2022-09-19 15:18
 * @desc
 **/
@Service
public class ReplyProvider {

    final
    RabbitTemplate rabbitTemplate;

    public ReplyProvider(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void send() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setReplyTo("replyQueue");
        Message message = new Message("测试消息".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("bizExchange","",message);
    }

    @RabbitListener(queues = "replyQueue")
    public void sendReply(Message message, Channel channel) throws IOException {
        String s = new String(message.getBody());
        System.out.println("收到客户端响应消息：" + s);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }


}
