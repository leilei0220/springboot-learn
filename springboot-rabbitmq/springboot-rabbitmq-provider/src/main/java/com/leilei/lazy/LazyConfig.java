package com.leilei.lazy;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lei
 * @date: 2025-07-31 17:45
 * @desc: 惰性队列
 */
@Configuration
public class LazyConfig {

    /**
     * 队列一
     * @return
     */
    @Bean
    public Queue fanoutQueueOne() {
        Map<String, Object> args = new HashMap<>(2);
        // 设置了此属性，mq会直接存在磁盘上不占用内存，当然这个模式消费要慢一些
        args.put("x-queue-mode", "lazy");
        return new Queue("lazy_queue",true,false,false,args);
    }

    /**
     * 交换机 声明为FanoutExchange类型
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("lazy_text_exchange");
    }

    @Bean
    public Binding bindingFanoutExchangeA(Queue fanoutQueueOne, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueOne).to(fanoutExchange);
    }

}
