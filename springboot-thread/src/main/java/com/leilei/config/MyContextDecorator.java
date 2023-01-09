package com.leilei.config;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.TaskDecorator;
/**
 * @author lei
 * @create 2023-01-09 17:14
 * @desc 自定义子线程装饰器
 **/
@Log4j2
public class MyContextDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        try {
            String s = ThreadContext.get();
            log.debug("尝试装饰子线程数据：{}",s);
            return () -> {
                try {
                    //将父线程的值设置进子线程里
                    ThreadContext.add(s);
                    //子线程方法执行
                    runnable.run();
                } finally {
                    //清除线程threadLocal的值
                    ThreadContext.remove();
                    log.info("子线程：{}清理自身线程数据", Thread.currentThread().getName());
                }
            };
        } catch (IllegalStateException e) {
            return runnable;
        }
    }
}