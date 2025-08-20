package com.leilei.translaction;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
// 2.3.3 版本使用 corePoolSize 属性代替 txProducerGroup
// 这里默认监听的rocketMQTemplate ，如果业务内有多个事务消息要发送，可以考虑 使用Message 中的header隔离区分，或者创建多个rocketMQTemplate
@RocketMQTransactionListener
public class OrderTransactionListener implements RocketMQLocalTransactionListener {

    private final ConcurrentHashMap<String, TransactionStatus> localTrans = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicInteger checkCounter = new AtomicInteger(0);

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String transactionId = getTransactionId(msg);
        System.out.println("执行本地事务: " + transactionId);
        
        try {
            // 模拟业务处理
            Thread.sleep(100);
            
            // 随机决定事务状态
            int result = counter.incrementAndGet();
            if (result % 4 == 0) {
                // 25% 概率成功
                localTrans.put(transactionId, TransactionStatus.COMMIT);
                System.out.println("✅ 本地事务执行成功: " + transactionId);
                return RocketMQLocalTransactionState.COMMIT;
            } else if (result % 4 == 1) {
                // 25% 概率失败
                localTrans.put(transactionId, TransactionStatus.ROLLBACK);
                System.out.println("❌ 本地事务执行失败: " + transactionId);
                return RocketMQLocalTransactionState.ROLLBACK;
            } else {
                // 50% 概率未知
                localTrans.put(transactionId, TransactionStatus.UNKNOWN);
                System.out.println("❓ 本地事务状态未知: " + transactionId);
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        } catch (Exception e) {
            System.err.println("本地事务异常: " + e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String transactionId = getTransactionId(msg);
        int checkCount = checkCounter.incrementAndGet();
        System.out.println("检查本地事务 [" + checkCount + "]: " + transactionId);
        
        TransactionStatus status = localTrans.getOrDefault(transactionId, TransactionStatus.UNKNOWN);
        
        switch (status) {
            case COMMIT:
                return RocketMQLocalTransactionState.COMMIT;
            case ROLLBACK:
                return RocketMQLocalTransactionState.ROLLBACK;
            default:
                // 模拟检查3次后自动提交
                if (checkCount % 3 == 0) {
                    System.out.println("⚠️ 事务检查超时，自动提交: " + transactionId);
                    return RocketMQLocalTransactionState.COMMIT;
                }
                return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
    
    private String getTransactionId(Message msg) {
        // 从消息头或payload获取事务ID
        Object txId = msg.getHeaders().get("tx_id");
        Object o = msg.getHeaders().get("businessType");
        if (o != null) {
            System.out.println("业务类型：" + o);
        }
        if (txId != null) return txId.toString();
        
        if (msg.getPayload() instanceof OrderMessage) {
            return ((OrderMessage) msg.getPayload()).getTransactionId();
        }
        
        return "UNKNOWN-TX-ID";
    }
    
    private enum TransactionStatus {
        COMMIT, ROLLBACK, UNKNOWN
    }
}