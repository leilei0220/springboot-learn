package com.leilei.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
 
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
 
    private static final String TOPIC = "test_topic";
 
    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        kafkaTemplate.send(TOPIC, message);
        return "Message sent successfully";
    }


    @GetMapping("/send2")
    public String sendMessage2() {
        kafkaTemplate.send(TOPIC, "some_key", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd Hh:mm:ss")));
        return "Message sent successfully";
    }
}
