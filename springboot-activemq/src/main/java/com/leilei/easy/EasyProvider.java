package com.leilei.easy;

import com.alibaba.fastjson.JSON;
import com.leilei.common.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author PengLei
 * @date 2020-07-17 10:59
 * @desc
 */
@Service
public class EasyProvider {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send() {
        for (int i = 0; i < 3; i++) {
            jmsMessagingTemplate.convertAndSend("active_easy_queue", JSON.toJSONString(new Driver(1L, "小司机")));
        }
    }
}
