package com.leilei.easy;

import com.leilei.common.Vehicle;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:21
 */
@Service
public class EasyProviderServer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEasyMessage() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("rabbit_easy_queue",new Vehicle(i,i+"车车"));
        }
    }

}
