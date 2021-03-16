package com.leilei.ttlanddead;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void sendMessage() {
        rabbitTemplate.convertAndSend("ttl-dead-exchange","test-ttl-and-dead", JSON.toJSONString(new Vehicle(1,"ttl+死信")));
    }
}
