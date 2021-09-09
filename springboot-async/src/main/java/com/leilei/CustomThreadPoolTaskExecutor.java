package com.leilei;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/13 22:20
 * @desc
 */
public class CustomThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {


    private static final Logger logger = LoggerFactory.getLogger(CustomThreadPoolTaskExecutor.class);

    private void showThreadPoolInfo(String doingMethod) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        logger.info("{}{} >>> taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                doingMethod,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("do execute runnable");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("do execute  runnable startTimeout");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("do submit runnable");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("do submit callable");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("do submitListenable runnable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("do submitListenable callable");
        return super.submitListenable(task);
    }
}