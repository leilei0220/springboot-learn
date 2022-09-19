package com.leilei.reply;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
     * @param channel
     * @return String
     * @author lei
     * @date 2022-09-19 16:17:52
     */
    @RabbitListener(queues ="bizQueue")
    @SendTo("replyQueue")
    public String handleEmailMessage(Message message, Channel channel) throws IOException {

        try {
            String msg=new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("---consumer接收到消息----{}",msg);
            return "客户端响应消息["+"收到的生产端消息为："+msg+"]";
        }
        catch (Exception e) {
            log.error("ReplyConsumerController.handleEmailMessage error",e);

        }
        return null;
    }

    /**
     * 方式2  message消息获取内部reply rabbitmq手动发送
     *
     * @param message
     * @param channel
     * @return String
     * @author lei
     * @date 2022-09-19 16:17:52
     */
    @RabbitListener(queues ="bizQueue")
    public void handleEmailMessage2(Message message, Channel channel) throws IOException {
        try {
            String msg=new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("---consumer接收到消息----{}",msg);
            String replyTo = message.getMessageProperties().getReplyTo();
            System.out.println("接收到的reply:"+replyTo);
            rabbitTemplate.convertAndSend(replyTo,"客户端响应消息["+"收到的生产端消息为："+msg+"]");
        }
        catch (Exception e) {
            log.error("ReplyConsumerController.handleEmailMessage error",e);
        }
    }

    /**
     * 方式三  方法有返回值,返回要响应的数据 （reply 由生产者发送消息时指定，消费者不做任何处理）
     *
     * @param message
     * @param channel
     * @return void
     * @author lei
     * @date 2022-09-19 17:42:09
     */
    @RabbitListener(queues ="bizQueue")
    public String handleEmailMessage3(Message message, Channel channel) throws IOException {
        try {
            String msg=new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("---consumer接收到消息----{}",msg);
            return "客户端响应消息["+"收到的生产端消息为："+msg+"]";
        }
        catch (Exception e) {
            log.error("ReplyConsumerController.handleEmailMessage error",e);
        }
        return null;
    }
}
