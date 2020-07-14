package com.leilei.fanoutexchange;

import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 22:12
 * @desc 发布订阅 生产者
 */
@Service
public class FanoutExchangeProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendFanoutExchangeMessage() {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("fanout_exchange","",new Vehicle(i,i+"发布订阅车车"));
        }
    }
}
