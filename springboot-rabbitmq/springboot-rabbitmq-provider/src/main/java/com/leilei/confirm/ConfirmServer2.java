package com.leilei.confirm;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/15 20:16
 * @desc
 */
@Service
public class ConfirmServer2 /*implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback*/{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 配置 confirm 机制
     */
    /**
     * 发送消息，并设置一个唯一消息ID
     */
    public void sendConfirm() {
            rabbitTemplate.convertAndSend("confirm_fanout_exchange",
                    "",
                    JSON.toJSONString(new Vehicle(1,"confirm功能的车车")),
                    new CorrelationData(UUID.randomUUID().toString()));
        // this.rabbitTemplate.setConfirmCallback(this);
    }

/*    *//**
     *
     * @param correlationData
     * @param ack
     * @param cause
     *//*
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("confirm 消息发送确认成功...消息ID为：" + correlationData.getId());
        } else {
            System.out.println("confirm 消息发送确认失败...消息ID为：" + correlationData.getId() + " 失败原因: " + cause);
        }
    }

    *//**
     * 当 发送消息到交换机或mq失败时，会进入returnedMessage方法，成功不会进入
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     *//*
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("message: " + SerializationUtils.deserialize(message.getBody()));
        System.out.println("replyCode: " + replyCode);
        System.out.println("replyText：" + replyText);
        System.out.println("exchange : " + exchange);
        System.out.println("routing : " + routingKey);
    }*/
}
