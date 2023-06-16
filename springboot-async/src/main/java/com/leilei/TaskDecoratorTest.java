package com.leilei;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lei
 * @create 2023-06-16 14:34
 * @desc TaskDecorator装饰器解决父子线程传值问题
 **/
public class TaskDecoratorTest {
    private static final Logger logger = LoggerFactory.getLogger(TaskDecoratorTest.class);

    public static void main(String[] args) throws InterruptedException {
        // 设置threadLocal 这里模拟为用户ID
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        // 使用spring 提供的线程池
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setKeepAliveSeconds(100);
        threadPoolTaskExecutor.setQueueCapacity(20);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(false);
        // 将原来的runnable包装一下
        threadPoolTaskExecutor.setTaskDecorator(runnable -> {
            // 外部获取到父线程信息
            String s = threadLocal.get();
            return () -> {
                // 内部补充runnable逻辑
                try {
                    String before = threadLocal.get();
                    // 将父线程数据，设置到当前线程
                    threadLocal.set(s);
                    logger.info("当前线程:{},原来线程变量为:{},设置父线程变量数据后为:{}", Thread.currentThread().getName(), before, s);
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 清理当前线程数据,线程池执行每次都会包装一下，如果这里不清理，那没有任务来的时候，当前线程的threadLocal就会一直存在了
                    threadLocal.remove();
                }
            };
        });
        threadPoolTaskExecutor.setThreadNamePrefix("lei-executor-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                super.rejectedExecution(r, e);
                logger.info("执行了拒绝策略");
            }
        });
        threadPoolTaskExecutor.initialize();
        threadLocal.set(Thread.currentThread().getName());
        for (int i = 0; i < 11; i++) {
            threadPoolTaskExecutor.execute(() -> logger.info("当前线程:{},获取到用户信息：{}", Thread.currentThread().getName(), threadLocal.get()));
        }

        TimeUnit.SECONDS.sleep(5);
        threadLocal.remove();
        logger.info("休息一会-----");
        threadPoolTaskExecutor.execute(() -> logger.info("当前线程:{},获取到用户信息：{}", Thread.currentThread().getName(), threadLocal.get()));
        threadPoolTaskExecutor.shutdown();
    }
}
