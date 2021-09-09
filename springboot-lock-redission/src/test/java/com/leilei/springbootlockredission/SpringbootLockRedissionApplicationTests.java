package com.leilei.springbootlockredission;

import com.leilei.SpringbootLockRedissionApplication;

import com.leilei.service.LockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(classes = SpringbootLockRedissionApplication.class)
@RunWith(SpringRunner.class)
public class SpringbootLockRedissionApplicationTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LockService lockService;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("aa","我的天!");
        final ExecutorService executorService = Executors.newFixedThreadPool(20);
        executorService.submit(() -> lockService.reentrantUnFairLock());
    }

}
