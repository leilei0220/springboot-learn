package com.leilei.translaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {
    private String transactionId; // 事务ID
    private String orderId;      // 订单ID
    private Double amount;       // 订单金额
}