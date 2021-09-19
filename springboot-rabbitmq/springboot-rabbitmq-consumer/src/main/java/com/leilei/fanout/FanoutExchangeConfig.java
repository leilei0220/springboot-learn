package com.leilei.fanout;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:58
 * @desc 发布订阅模式 配置两个队列一个交换机
 */
@Configuration
public class FanoutExchangeConfig {
    /**
     * 队列一
     * @return
     */
    @Bean
    public Queue fanoutQueueOne() {
        return new Queue("rabbit_fanout_queue_one");
    }
    /**
     * 队列二
     * @return
     */
    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue("rabbit_fanout_queue_two");
    }
    /**
     * 交换机 声明为FanoutExchange类型
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }

    /**
     * 绑定队列一到交换机
     * @param fanoutQueueOne 上方定义的队列一方法名  根据此方法名参数 器会自动注入对应bean
     * @param fanoutExchange 上方定义的交换机方法名
     * @return
     */
    @Bean
    public Binding bindingFanoutExchangeA(Queue fanoutQueueOne, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueOne).to(fanoutExchange);
    }

    /**
     * 绑定队列二到交换机
     * @param fanoutQueueTwo  上方定义的队列二方法名  根据此方法名参数 器会自动注入对应bean   当
     *                        然也可以省略参数 直接在bind中指定队列构建方法名 例如 FanoutQueueTwo()
     *
     * @param fanoutExchange 上方定义的交换机方法名
     * @return
     */
    @Bean
    public Binding bindingFanoutExchangeB(Queue fanoutQueueTwo, FanoutExchange  fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueTwo).to(fanoutExchange);
    }
}
