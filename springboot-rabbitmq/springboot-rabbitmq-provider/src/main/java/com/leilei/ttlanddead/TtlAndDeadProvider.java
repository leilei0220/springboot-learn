package com.leilei.ttlanddead;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author lei
 * @version 1.0
 * @desc
 * @date 2021-03-16 16:35
 */
@Service
public class TtlAndDeadProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 固定延迟 ttl队列设置了x-message-ttl属性，时间到了后，消息会流转至死信队列
     */
    public void sendMessage() {
        rabbitTemplate.convertAndSend("ttl-dead-exchange","test-ttl-and-dead", JSON.toJSONString(new Vehicle(1,"ttl+死信")));
    }


    /**
     * 动态延迟 ,ttl队列Bean定义需要删除x-message-ttl属性,以传入为准，传入时间到了后，消息会流转至死信队列
     */
    public void sendMessage2(long expirationTime) {
        rabbitTemplate.convertAndSend("ttl-dead-exchange","test-ttl-and-dead", JSON.toJSONString(new Vehicle(1,"ttl+死信")),message -> {
            message.getMessageProperties().setExpiration(Long.toString(expirationTime));
            message.getMessageProperties().setContentEncoding("UTF-8");
            System.out.println("延迟消息发送时间：" + LocalDateTime.now());
            return message;
        });
    }
}
