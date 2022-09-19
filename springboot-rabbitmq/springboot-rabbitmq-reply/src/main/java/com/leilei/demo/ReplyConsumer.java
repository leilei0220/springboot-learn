package com.leilei.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author lei
 * @create 2022-09-19 15:22
 * @desc reply模式 消费者消费消息后给生产者发送回应消息
 **/
@Component
@Log4j2
public class ReplyConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 方式1   SendTo指定响应队列
     *
     * @param message
     * @return String
     * @author lei
     * @date 2022-09-19 16:17:52
     */
    // @RabbitListener(queues ="bizQueue")
    // @SendTo("replyQueue")
    // public String handleEmailMessage(Message message) {
    //     try {
    //         String msg=new String(message.getBody(), StandardCharsets.UTF_8);
    //         log.info("---consumer接收到消息----{}",msg);
    //         return "客户端响应消息:"+msg+"处理完成!";
    //     } catch (Exception e) {
    //         log.error("处理业务消息失败",e);
    //     }
    //     return null;
    // }


    /**
     * 方式2  message消息获取内部reply rabbitmq手动发送
     *
     * @param message
     * @return String
     * @author lei
     * @date 2022-09-19 16:17:52
     */
    // @RabbitListener(queues = "bizQueue")
    // public void handleEmailMessage2(Message message) {
    //     try {
    //         String msg = new String(message.getBody(), StandardCharsets.UTF_8);
    //         log.info("---consumer接收到消息----{}", msg);
    //         String replyTo = message.getMessageProperties().getReplyTo();
    //         System.out.println("接收到的reply:" + replyTo);
    //         rabbitTemplate.convertAndSend(replyTo, "客户端响应消息:" + msg + "处理完成!", x -> {
    //             x.getMessageProperties().setCorrelationId(message.getMessageProperties().getCorrelationId());
    //             return x;
    //         });
    //     } catch (Exception e) {
    //         log.error("处理业务消息失败",e);
    //     }
    // }

    /**
     * 方式三  方法有返回值,返回要响应的数据 （reply 由生产者发送消息时指定，消费者不做任何处理）
     *
     * @param message
     * @return void
     * @author lei
     * @date 2022-09-19 17:42:09
     */
    @RabbitListener(queues ="bizQueue")
    public String handleEmailMessage3(Message message) {
        try {
            String msg=new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("---consumer接收到消息----{}",msg);
            return "客户端响应消息:"+msg+"处理完成!";
        }
        catch (Exception e) {
            log.error("处理业务消息失败",e);
        }
        return null;
    }
}
