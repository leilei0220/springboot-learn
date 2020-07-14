package com.leilei;

import com.leilei.directexchange.DirectExchangeProvider;
import com.leilei.easy.EasyProviderServer;
import com.leilei.fanoutexchange.FanoutExchangeProvider;
import com.leilei.work.WorkProviderServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {
    @Autowired
    private EasyProviderServer easyProviderServer;
    @Autowired
    private WorkProviderServer workProviderServer;
    @Autowired
    private FanoutExchangeProvider fanoutExchangeProvider;
    @Autowired
    private DirectExchangeProvider directExchangeProvider;
    @Test
    void contextLoads() {
        /**
         * 最简单使用 一个生产者一个消费者
         */
        easyProviderServer.sendEasyMessage();
    }

    @Test
    void test() {
        /**
         * 一个生产者 多个消费者 生产者生产的消息被平分到消费者 例如 十条消息 有两个消费者 则每个消费者会消费五次
         */
        workProviderServer.sendWorkMessage();
    }

    @Test
    void FanoutExchange() {
        /**
         * 发布订阅模式 订阅到交换机的队列 都会获取发布的消息，每一个队列都会被消费者消费设置次数 例如我生产者生产消息循环20次 则 队列一 队列二会被消费二十次
         */
        fanoutExchangeProvider.sendFanoutExchangeMessage();
    }

    /**
     * 路由模式 测试发送到  lei_routingKey_one 键  ，按道理来说则会被我们的队列rabbit_direct_queue_one 监听到
     *  结果正确  rabbit_direct_queue_one 的两个消费者轮流接收消费到了五条消息 一个消费者三条 一个消费者两条 共五条
     *
     *  rabbit_direct_queue_one队列 消费者1：收到消息---Vehicle(id=1, name=1理由键lei_routingKey_one车车)
     * rabbit_direct_queue_one队列 消费者2：收到消息---Vehicle(id=0, name=0理由键lei_routingKey_one车车)
     * rabbit_direct_queue_one队列 消费者1：收到消息---Vehicle(id=3, name=3理由键lei_routingKey_one车车)
     * 2020-07-14 22:58:23.786  INFO 18280 --- [extShutdownHook] o.s.a.r.l.SimpleMessageListenerContainer : Waiting for workers to finish.
     * rabbit_direct_queue_one队列 消费者2：收到消息---Vehicle(id=2, name=2理由键lei_routingKey_one车车)
     * rabbit_direct_queue_one队列 消费者2：收到消息---Vehicle(id=4, name=4理由键lei_routingKey_one车车)
     *
     */
    @Test
    void DirectExchange() {
        directExchangeProvider.sendDirectMessageOne();
    }
    /**
     * 路由模式 测试发送到  lei_routingKey_two 键  ，按道理来说则会被我们的队列rabbit_direct_queue_two 监听到
     *  结果正确 rabbit_direct_queue_two 一个消费者轮流接收消费了五条消息
     *  rabbit_direct_queue_two队列 ：收到消息---Vehicle(id=0, name=0理由键lei_routingKey_two车车)
     * rabbit_direct_queue_two队列 ：收到消息---Vehicle(id=1, name=1理由键lei_routingKey_two车车)
     * rabbit_direct_queue_two队列 ：收到消息---Vehicle(id=2, name=2理由键lei_routingKey_two车车)
     * rabbit_direct_queue_two队列 ：收到消息---Vehicle(id=3, name=3理由键lei_routingKey_two车车)
     * rabbit_direct_queue_two队列 ：收到消息---Vehicle(id=4, name=4理由键lei_routingKey_two车车)
     */
    @Test
    void DirectExchange2() {
        directExchangeProvider.sendDirectMessageTwo();
    }


}
