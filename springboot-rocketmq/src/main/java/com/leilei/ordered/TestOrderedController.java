package com.leilei.ordered;

import com.leilei.translaction.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestOrderedController {

    @Resource
    private TestOrderedService testOrderedService;

    @GetMapping("/ordered")
    public String createOrder(String orderId) {
        testOrderedService.sendOrderlyMessages(orderId);
        return "顺序消息已发送!";
    }
}