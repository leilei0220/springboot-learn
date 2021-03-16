package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lei
 * @version 1.0
 * @desc rabbitmq 生产者生产消息模拟
 * @date 2021-03-16 10:07
 */
@SpringBootApplication
public class RabbitmqProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProviderApplication.class, args);
    }

}
