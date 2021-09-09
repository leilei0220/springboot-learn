package com.leilei.controller;

import com.leilei.service.LockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2021-09-09 15:55
 * @desc 测试锁
 **/
@RestController
public class RedissionLockController {
    private final LockService lockService;

    public RedissionLockController(LockService lockService) {
        this.lockService = lockService;
    }

    //--------模拟线程间 排队等待(每个线程最终都会执行) （公平 非公平）
    /**
     * 测试可重入非公平锁
     */
    @GetMapping("/lock/un/fair")
    public void reentrantUnFairLock() {
        lockService.reentrantUnFairLock();
    }

    /**
     * 测试可重入公平锁
     */
    @GetMapping("/lock/fair")
    public void reentrantFairLock() {
        lockService.reentrantFairLock();
    }

    //--------模拟线程间 抢锁（多个并发线程 最终只会执行一个） （公平 非公平）

    @GetMapping("/lock/grab/fair")
    public String threadGrabLock() {
        return lockService.threadGrabLock();
    }
}
