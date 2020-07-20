package com.leilei.topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

/**
 * @author PengLei
 * @date 2020-07-17 11:42
 * @desc
 */
@Configuration
public class ActiveTopicConfig {
    @Bean
    public Topic topic() {
        return new ActiveMQTopic("lei_topic") ;
    }
    /**
     * springboot默认只配置queue类型消息，如果要使用topic类型的消息，则需要配置该bean
     * @param connectionFactory  factory.setPubSubDomain(true);
     * @return
     */
    @Bean
    public JmsListenerContainerFactory leiTopicFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //这里必须设置为true，false则表示是queue类型 虚拟主题（virtual则必须设为false）
        factory.setPubSubDomain(true);
        return factory;
    }
}
