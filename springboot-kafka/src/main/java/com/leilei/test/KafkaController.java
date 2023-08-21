package com.leilei.test;

import com.alibaba.fastjson.JSON;
import com.leilei.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${kafka.calcTopic}")
    private String topic;
 
    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        kafkaTemplate.send(topic, message);
        return "Message sent successfully";
    }


    @GetMapping("/send2/{sendNum}")
    public String sendMessage2(@PathVariable Integer sendNum) {
        for (int i = 0; i < sendNum; i++) {
            Location location = new Location();
            location.setPlate("川A000-" + i + 1);
            location.setColor("蓝");
            location.setSendTime(LocalDateTime.now());
            kafkaTemplate.send(topic, JSON.toJSONString(location));
        }
        return sendNum + "条定位信息已发送完成";
    }

}
