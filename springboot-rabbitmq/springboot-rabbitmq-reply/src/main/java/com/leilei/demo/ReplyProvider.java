package com.leilei.demo;

import com.alibaba.fastjson.JSON;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    /**
     * 生产消息
     *
     * @param
     * @return void
     * @author lei
     * @date 2022-09-19 21:59:18
     */
    public void replySend() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setReplyTo("replyQueue");
        //todo 根据业务，做一个严谨的全局唯一ID,我这里暂时用UUID
        String correlationId = UUID.randomUUID().toString();
        // 我这里指定了唯一消息ID，看业务场景,消费者消费响应后，生产者端可根据消息ID做业务处理
        messageProperties.setCorrelationId(correlationId);
        Vehicle vehicle = new Vehicle(1, "川A0001");
        Message message = new Message(JSON.toJSONString(vehicle).getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("bizExchange","",message);
        System.out.println("生产者发送消息,自定义消息ID为：" + correlationId);
    }

    /**
     * 接收消息响应
     *
     * @param message
     * @return void
     * @author lei
     * @date 2022-09-19 21:59:27
     */
    @RabbitListener(queues = "replyQueue")
    public void replyResponse(Message message) {
        String s = new String(message.getBody());
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("收到客户端响应消息ID：" + correlationId);
        //todo 根据消息ID可判断这是哪一个消息的响应,我们就可做业务操作
        System.out.println("收到客户端响应消息：" + s);
    }


}
