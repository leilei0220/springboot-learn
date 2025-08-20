package com.leilei.translaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create-order")
    public String createOrder() {
        orderService.createOrder();
        return "事务消息已发送!";
    }
}