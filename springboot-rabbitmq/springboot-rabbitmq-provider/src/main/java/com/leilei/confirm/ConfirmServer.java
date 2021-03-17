// package com.leilei.confirm;
//
// import com.leilei.common.Vehicle;
// import org.springframework.amqp.rabbit.connection.CorrelationData;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import java.util.UUID;
//
// /**
//  * @author lei
//  * @version 1.0
//  * @date 2020/7/15 20:16
//  * @desc
//  */
// @Service
// public class ConfirmServer {
//     @Autowired
//     private RabbitTemplate rabbitTemplate;
//     /**
//      * 配置 confirm 机制
//      */
//     private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
//         /**
//          *
//          * @param correlationData 消息相关的数据，一般用于获取 唯一标识 id
//          * @param b 是否发送成功
//          * @param error 失败原因
//          */
//         @Override
//         public void confirm(CorrelationData correlationData, boolean b, String error) {
//             if (b) {
//                 System.out.println("confirm 消息发送确认成功...消息ID为：" + correlationData.getId());
//             } else {
//                 System.out.println("confirm 消息发送确认失败...消息ID为：" + correlationData.getId() + " 失败原因: " + error);
//             }
//         }
//     };
//     /**
//      * 发送消息，并设置一个唯一消息ID
//      */
//     public void sendConfirm() {
//             rabbitTemplate.convertAndSend("confirm_fanout_exchange",
//                     "",
//                     new Vehicle(1,"confirm功能的车车"),
//                     new CorrelationData(UUID.randomUUID().toString()));
//             rabbitTemplate.setConfirmCallback(confirmCallback);
//     }
// }
