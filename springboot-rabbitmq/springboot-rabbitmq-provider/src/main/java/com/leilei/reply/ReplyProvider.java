package com.leilei.reply;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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
        Vehicle vehicle = new Vehicle(1, "川A0001");
        Message message = new Message(JSON.toJSONString(vehicle).getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("bizExchange","",message);
    }

    @RabbitListener(queues = "replyQueue")
    public void sendReply(Message message) {
        String s = new String(message.getBody());
        System.out.println("收到客户端响应消息：" + s);
    }


}
