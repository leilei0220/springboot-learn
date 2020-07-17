package com.leilei.easy;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @author PengLei
 * @date 2020/7/17 0017.
 * @desc
 */
@Configuration
public class EasyActiveConfig {
    @Bean
    public Queue easyQueue() {
        return new ActiveMQQueue("active_easy_queue");
    }

}
