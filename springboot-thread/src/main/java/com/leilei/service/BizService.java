package com.leilei.service;

import com.leilei.config.ThreadContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lei
 * @create 2023-01-09 17:20
 * @desc 业务服务
 **/
@Service
@Log4j2
public class BizService {

    @Qualifier("bizExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void execute() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("请求线程：{},当前子线程:{},获取父线程数据:{}", name, Thread.currentThread().getName(), ThreadContext.get());
            });
        }
    }
}
