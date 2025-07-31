package com.leilei.ttl;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @desc
 * @date 2021-03-16 16:22
 */
@Service
public class TtlProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        rabbitTemplate.convertAndSend("ttl-exchange", "", JSON.toJSONString(new Vehicle(1, "测试ttl")));
    }

    /**
     * 设置消息存活时间为6秒
     */
    public void sendMessage2() {
        rabbitTemplate.convertAndSend("ttl-exchange", "", JSON.toJSONString(new Vehicle(2, "测试ttl2")),message -> {
            message.getMessageProperties().setExpiration(1000 * 5 + "");
            return message;
        });
    }

    /**
     * 设置两个不同长短的存活时间,存活时间长的先入列,那如果长的没有消亡,短的也不会消亡（控制台消息不会少）
     * 但如果有消费者后续来消费这个TTL消息的话，如果有已超过存活时间消息,则该条不会被消费 (惰性删除，过期消息在访问时才会判断然后选择删除)
     * @author lei
     * @date 2025-07-30 17:50:43
     * @param
     * @return void
     */
    public void sendMessage3() {
        rabbitTemplate.convertAndSend("ttl-exchange", "", JSON.toJSONString(new Vehicle(1, "ttl长的")),message -> {
            message.getMessageProperties().setExpiration(1000 * 155 + "");
            return message;
        });

        rabbitTemplate.convertAndSend("ttl-exchange", "", JSON.toJSONString(new Vehicle(2, "ttl短的")),message -> {
            message.getMessageProperties().setExpiration(1000 * 5 + "");
            return message;
        });
    }
}
