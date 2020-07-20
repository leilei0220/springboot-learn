package com.leilei.virtual;
 
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

/**
 * @author PengLei
 * @date 2020-07-17 21:32
 * @desc
 */
@Configuration
@EnableJms
public class VirtualConfig {


    private static final String VIRTUAL_TOPIC_NAME = "VirtualTopic.Orders";


    @Bean("virtualTopicQueue")
    public ActiveMQTopic virtualTopicQueue(){
        return new ActiveMQTopic(VIRTUAL_TOPIC_NAME);
    }

    /**
     * 特别特别注意配置虚拟主题 需要设为false
     * @param connectionFactory
     * @return
     */
    @Bean(name = "virtualFactory")
    public JmsListenerContainerFactory<?> virtualFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}