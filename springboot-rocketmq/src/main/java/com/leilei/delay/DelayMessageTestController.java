package com.leilei.delay;

import com.leilei.translaction.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lei
 * @date: 2025-08-20 11:23
 * @desc:
 */
@RestController
public class DelayMessageTestController {

    @Autowired
    private DelayMessageService delayMessageService;

    @GetMapping("/sendDelay")
    public String createOrder() {
        delayMessageService.sendDelayedMessage();
        return "延迟消息已发送!";
    }
}
