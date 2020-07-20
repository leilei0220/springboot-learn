package com.leilei.topic;

import com.leilei.common.Driver;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author PengLei
 * @date 2020-07-17 13:26
 * @desc
 */
@Component
public class ActiveTopicConsumer {
    /**
     * destination 为定义的topic的名字
     * concurrency 消费者并发数
     * containerFactory 之前设置的JmsListenerContainerFactory bean 名字
     * @param driver
     */
    @JmsListener(destination="lei_topic", concurrency = "1",containerFactory = "leiTopicFactory")
    public void ListenTopic(Driver driver){
        System.out.println(Thread.currentThread().getName()+"消费者1接收到topic消息：" + driver);
    }
    @JmsListener(destination="lei_topic",concurrency = "10", containerFactory = "leiTopicFactory")
    public void ListenTopic2(Driver driver){
        System.out.println(Thread.currentThread().getName()+"--消费者--2--接收到topic消息：" + driver);
    }
}
