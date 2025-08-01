package com.leilei.lazy;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lei
 * @date: 2025-07-31 17:48
 * @desc:
 */
@Service
public class LazyProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTask(String lazyInfo) {
        rabbitTemplate.convertAndSend("lazy_text_exchange", "", lazyInfo);
    }
}
