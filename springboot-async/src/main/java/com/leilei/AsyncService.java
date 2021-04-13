package com.leilei;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/13 22:20
 * @desc
 */
@Service
public class AsyncService {
    @Async
    public void eat() {
        System.out.println(Thread.currentThread().getName() + "吃东西了");
    }
}
