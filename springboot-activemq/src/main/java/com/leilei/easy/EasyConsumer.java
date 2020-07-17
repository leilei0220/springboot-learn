package com.leilei.easy;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Driver;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author PengLei
 * @date 2020-07-17 11:02
 * @desc
 */
@Component
public class EasyConsumer {
    @JmsListener(destination = "active_easy_queue")
    public void aa(String aa) {
        System.out.println("收到简单的消息" + JSON.parseObject(aa, Driver.class));
    }
}
