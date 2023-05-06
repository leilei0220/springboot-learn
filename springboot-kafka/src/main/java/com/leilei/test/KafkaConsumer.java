package com.leilei.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {

    /**
     * 发送数据没有key 可以简单使用string接收也行
     *
     * @param consumerRecord
     */
    // @KafkaListener(topics = "test_topic", groupId = "group_id")
    // public void consume(String message) {
    //     System.out.println("Consumed message: " + message);
    // }

    /**
     * 单条消费  spring.kafka.listener.type=single
     * @param consumerRecord
     */
    @KafkaListener(topics = "test_topic", groupId = "group_id")
    public void consume1(ConsumerRecord<String, String> consumerRecord) {
        System.out.println("收到单条消息");
        System.out.println(consumerRecord);
        System.out.println("Consumed message: " + consumerRecord.key() + "--" + consumerRecord.value());
    }


}
