package com.leilei;

import com.leilei.confirm.ConfirmServer2;
import com.leilei.delayed.DelayedProvider;
import com.leilei.direct.DirectExchangeProvider;
import com.leilei.easy.EasyProviderServer;
import com.leilei.fanout.FanoutExchangeProvider;
import com.leilei.reply.ReplyProvider;
import com.leilei.topic.TopicRabbitProvider;
import com.leilei.ttl.TtlProvider;
import com.leilei.ttlanddead.TtlAndDeadProvider;
import com.leilei.work.WorkProviderServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqProviderApplicationTests {
    @Autowired(required = false)
    private EasyProviderServer easyProviderServer;
    @Autowired(required = false)
    private WorkProviderServer workProviderServer;
    @Autowired(required = false)
    private FanoutExchangeProvider fanoutExchangeProvider;
    @Autowired(required = false)
    private DirectExchangeProvider directExchangeProvider;
    @Autowired(required = false)
    private TopicRabbitProvider topicRabbitProvider;
    @Autowired(required = false)
    private TtlProvider ttlProvider;
    @Autowired(required = false)
    private TtlAndDeadProvider ttlAndDeadProvider;
    @Autowired(required = false)
    private ConfirmServer2 confirmServer;
    @Autowired(required = false)
    private ReplyProvider replyProvider;

    @Autowired(required = false)
    private DelayedProvider delayedProvider;
    @Test
    void contextLoads() {
        /**
         * 最简单使用 一个生产者一个消费者
         */
        easyProviderServer.sendEasyMessage();
    }

    @Test
    void testWork() {
        /**
         * 一个生产者 多个消费者 生产者生产的消息被平分到消费者 例如 十条消息 有两个消费者 则每个消费者会消费五次
         */
        workProviderServer.sendWorkMessage();
    }

    @Test
    void FanoutExchange() {
        /**
         * 发布订阅模式 订阅到交换机的队列 都会获取发布的消息，例如我生产者生产消息循环20次 则 队列一 队列二均会被消费二十次
         */
        fanoutExchangeProvider.sendFanoutExchangeMessage();
    }

    /**
     * 路由模式 测试发送到  lei_routingKey_one 键  ，按道理来说则会被我们的队列rabbit_direct_queue_one 监听到
     *  结果正确  rabbit_direct_queue_one 的两个消费者轮流接收消费到了五条消息 一个消费者三条 一个消费者两条 共五条
     *
     rabbit_direct_queue_one队列 消费者1：收到消息---Vehicle(id=0, name=0路由键lei_routingKey_one车车)
     rabbit_direct_queue_one队列 消费者2：收到消息---Vehicle(id=2, name=2路由键lei_routingKey_one车车)
     rabbit_direct_queue_one队列 消费者1：收到消息---Vehicle(id=1, name=1路由键lei_routingKey_one车车)

     rabbit_direct_queue_one队列 消费者2：收到消息---Vehicle(id=3, name=3路由键lei_routingKey_one车车)
     rabbit_direct_queue_one队列 消费者1：收到消息---Vehicle(id=4, name=4路由键lei_routingKey_one车车)
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

    /**
     * 监听到队列二消息Vehicle(id=0, name=0一个词路由键车车)
     * 监听到队列一消息Vehicle(id=0, name=0一个词路由键车车)
     * 监听到队列一消息Vehicle(id=1, name=1多个词路由键车车)
     * 监听到队列二消息Vehicle(id=2, name=2一个词路由键车车)
     * 监听到队列一消息Vehicle(id=2, name=2一个词路由键车车)
     * 监听到队列一消息Vehicle(id=3, name=3多个词路由键车车)
     * 监听到队列二消息Vehicle(id=4, name=4一个词路由键车车)
     * 监听到队列一消息Vehicle(id=4, name=4一个词路由键车车)
     * 监听到队列一消息Vehicle(id=5, name=5多个词路由键车车)
     * 监听到队列一消息Vehicle(id=6, name=6一个词路由键车车)
     * 监听到队列二消息Vehicle(id=6, name=6一个词路由键车车)
     * 监听到队列一消息Vehicle(id=7, name=7多个词路由键车车)
     * 监听到队列二消息Vehicle(id=8, name=8一个词路由键车车)
     * 监听到队列一消息Vehicle(id=8, name=8一个词路由键车车)
     * 监听到队列一消息Vehicle(id=9, name=9多个词路由键车车)
     *
     * 队列一绑定的是 topic.# ,其结果 发送到 路由键 topic.lei.xxl 与 topic.lei 的消息都被接受了  一个词多个词消息都被队列一监听到消费者消费了
     * 队列二 绑定路由键是 * 其结果只有%2==0的消息被接受了，即 topic.lei  仅仅监听到了一个词路由键的消息
     */

    @Test
    void Topic() {
        topicRabbitProvider.sendTopMessage();
    }

    /**
     * ttl 过期队列,当一定时间内没有被消费的话，会删除,
     * 当设置的队列属性x-message-ttl 和单独消息设置 message.getMessageProperties().setExpiration();不一致时，会采用取最小值
     * 如果，多个消息设置了TTL,但时间长短不一致,比如TTL长的先入列，TTL短的后入列,此时TTL长的未删除前，短的也不会被删除（队列，先进先出，先一个没死后一个不会检测）
     */
    @Test
    void ttl() {
        // ttlProvider.sendMessage();
        // ttlProvider.sendMessage2();
        ttlProvider.sendMessage3();
    }

    /**
     * ttl 过期队列+死信
     */
    @Test
    void ttlAndDead() {
        ttlAndDeadProvider.sendMessage();
    }

    /**
     * 当前时间:2023-05-18T11:46:58.951,死信队列收到消息：Vehicle(id=1, name=ttl+死信, sendDate=2023-05-18T11:44:28.778)
     * 当前时间:2023-05-18T11:46:58.953,死信队列收到消息：Vehicle(id=1, name=ttl+死信, sendDate=2023-05-18T11:44:49.743)
     */
    @Test
    void ttlAndDead2() {
        ttlAndDeadProvider.sendMessage2(1,40000);
        // 头阻塞问题导致，即使第二个消息延时时间远远小于前一个，但还是需要等前一个消息出列后第二个才会被消费
        ttlAndDeadProvider.sendMessage2(2,4000);

        ttlAndDeadProvider.sendMessage2(3,15000);
    }

    /**
     * confirm 消息发送确认成功...消息ID为：1594825498345
     * 正常收到消息：Optional[Vehicle(id=1, name=confirm功能的车车)]
     */
    @Test
    void testConfirm() {
        confirmServer.sendConfirm();
    }

    /**
     * 测试延迟队列
     */
    @Test
    public void testDelayed() {
        delayedProvider.send(5000, "中秋节快乐!");
    }

    @Test
    public void testReply() {
        replyProvider.send();
    }
}
