package com.leilei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lei
 * @create 2023-01-09 16:16
 * @desc 线程池配置
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor bizExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        taskExecutor.setKeepAliveSeconds(40);
        taskExecutor.setQueueCapacity(1024);
        taskExecutor.setTaskDecorator(new MyContextDecorator());
        taskExecutor.setThreadNamePrefix("bizExecutor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
