package com.leilei.direct;

import com.alibaba.fastjson.JSON;
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
public class DirectExchangeProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 三个参数 交换机 路由键1 消息
     */
    public void sendDirectMessageOne() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("direct_exchange",
                    "lei_routingKey_one",
                    JSON.toJSONString( new Vehicle(i, i + "路由键lei_routingKey_one车车")));
        }
    }

    /**
     * 三个参数 交换机 路由键2 消息
     */
    public void sendDirectMessageTwo() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("direct_exchange",
                    "lei_routingKey_two",
                    JSON.toJSONString(  new Vehicle(i, i + "路由键lei_routingKey_two车车")));
        }
    }
}
