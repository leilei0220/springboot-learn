package com.leilei.work;

import com.alibaba.fastjson.JSON;
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
public class WorkProviderServer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendWorkMessage() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("rabbit_work_queue", JSON.toJSONString(new Vehicle(i,i+"work车车")));
        }
    }

}
