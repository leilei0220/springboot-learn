package com.leilei;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.stereotype.Service;


/**
 * @author: lei
 * @date: 2025-06-30 14:39
 * @desc: 这个类可以全局监听所有的重试方法
 */
@Service
public class RetryListener implements org.springframework.retry.RetryListener {
    @Override
    public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
        System.out.println("监听有重试注解的方法");
        // 返回true才会执行
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
        System.out.println("结束执行方法");

    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
        System.out.println("方法执行异常");
    }
}
