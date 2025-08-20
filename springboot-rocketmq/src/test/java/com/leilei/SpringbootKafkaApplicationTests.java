package com.leilei;

import com.leilei.demo.MyProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootKafkaApplicationTests {

    @Resource
    private MyProducer producer;


    @Test
    void contextLoads() {
        producer.sendMessage("TestTopic", "Hello RocketMQ 1");
        producer.sendMessage("TestTopic", "Hello RocketMQ 2");
    }
}
