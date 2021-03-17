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
}
