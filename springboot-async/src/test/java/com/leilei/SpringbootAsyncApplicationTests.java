package com.leilei;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAsyncApplicationTests {
    @Autowired
    private DemoService demoService;

    @Autowired
    private AsyncService asyncService;

    /**
     * 当前线程:main
     * asyncExecutor-1吃东西了
     */
    @Test
    public void testAsync() {
        System.out.println("当前线程:"+Thread.currentThread().getName());
        asyncService.eat();
    }

    /**
     * 当前线程:main
     * null (无法拿到返回值)
     * 线程：asyncExecutor-1调用了eat方法  （因为我demoService 注入了自己的线程池，因此调用此类异步方法都会使用自己线程池线程执行）
     */
    @Test
   public void testAsyncAndAsync() {
        System.out.println("当前线程:"+Thread.currentThread().getName());
        String name = demoService.testEat();
        System.out.println(name);
    }

    /**
     * 方式一：直接调用调用同一个类中异步方法
     * 当前线程:main
     * 线程：asyncExecutor-1开始跑步
     * 线程：asyncExecutor-1调用了本类含有异步注解方法
     *
     *
     * 方式一：注入自己（代理类） 调用同一个类中异步方法
     * 当前线程:main
     * 线程：asyncExecutor-1调用了本类含有异步注解方法
     * 线程：asyncExecutor-2开始跑步
     */
    @Test
    public void testRun() {
        System.out.println("当前线程:"+Thread.currentThread().getName());
        demoService.testRun();
    }

    /**
     * completableFuture
     * 当前线程:main
     * 线程：ForkJoinPool.commonPool-worker-1 蹦蹦跳跳
     */
    @Test
    public void testJump() {
        System.out.println("当前线程:"+Thread.currentThread().getName());
        demoService.jump();
    }

    /**
     * completableFuture+自定义线程池
     * 当前线程:main
     * 线程：asyncExecutor-1 蹦蹦跳跳
     * asyncExecutor-1
     */
    @Test
    public void testJumpAndExecutor() {
        System.out.println("当前线程:"+Thread.currentThread().getName());
        String s = demoService.jumpAndExecutor();
        System.out.println(s);
    }


}
