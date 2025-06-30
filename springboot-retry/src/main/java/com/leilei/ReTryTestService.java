package com.leilei;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * @author: lei
 * @date: 2025-06-30 14:15
 * @desc:
 */
@Service
public class ReTryTestService {

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public String testReTry(String uuid) {
        System.out.println("开始执行" + LocalDateTime.now() + ",参数为:" + uuid);
        throw new RuntimeException("测试异常");
    }




    @Recover
    public String  recover(Exception e,String uuid) {
        System.out.println("开始执行回调方法" + LocalDateTime.now()+ ",参数为:" + uuid);
        return "回调返回值";
    }
}
