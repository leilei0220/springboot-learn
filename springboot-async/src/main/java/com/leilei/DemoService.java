package com.leilei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/13 22:19
 * @desc
 */
@Service
public class DemoService {

    private final AsyncService asyncService;
    private final DemoService demoService;
    private final Executor asyncExecutor;

    @Autowired
    public DemoService(AsyncService asyncService, @Lazy DemoService demoService,
                       @Qualifier("asyncExecutor") Executor asyncExecutor) {
        this.asyncService = asyncService;
        this.demoService = demoService;
        this.asyncExecutor = asyncExecutor;
    }


    @Async
    public String testEat() {
        String name = Thread.currentThread().getName();
        asyncService.eat();
        System.out.println("线程：" + name + "调用了eat方法");
        return name;
    }

    @Async("asyncExecutor")
    public void testRun() {
        String name = Thread.currentThread().getName();
        // 注入形式注入自己（实际是由于Lazy注入了代理类 异步会生效 ）
        demoService.running();
        // 直接调用同类中异步 异步会失效，仍会使用当前线程执行 running()
        //running();
        System.out.println("线程：" + name + "调用了本类含有异步注解方法");
    }

    @Async
    public void running() {
        String name = Thread.currentThread().getName();
        System.out.println("线程：" + name + "开始跑步");
    }


    public void jump() {
        CompletableFuture.runAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println("线程：" + name + " 蹦蹦跳跳");
        });
    }

    public String jumpAndExecutor() {
        CompletableFuture<String> jumpAndExecutorThreadName = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println("线程：" + name + " 蹦蹦跳跳");
            return name;
        }, asyncExecutor);
        return jumpAndExecutorThreadName.join();
    }
}
