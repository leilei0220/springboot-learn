package com.leilei;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lei
 * @create 2023-07-05 17:26
 * @desc 阿里 可转让线程变量框架使用
 **/
public class TransmitTableThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> userThreadLocal = new TransmittableThreadLocal<>();
        String mainUser = "张三";
        System.out.println("主线程：" + Thread.currentThread().getName() + " 设置值:" + mainUser);
        // ThreadLocal 默认set get 是以当前调用线程,这里set这就是main线程
        userThreadLocal.set(mainUser);
        String s = userThreadLocal.get();
        System.out.println("当前线程：" + Thread.currentThread().getName() + " 获取值:" + s);

        ExecutorService threadPool =TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1)) ;
        threadPool.execute(() -> {
            String s1 = userThreadLocal.get();
            // 这里会获取到值,TransmittableThreadLocal做了包装处理，每次调用任务的时，都会将当前的主线程的TTL数据copy到子线程里面
            System.out.println("当前线程：" + Thread.currentThread().getName() + "第一次获取值:" + s1);
        });
        Thread.sleep(1000);
        mainUser = "张三第二次设置";
        userThreadLocal.set(mainUser);
        System.out.println("主线程：" + Thread.currentThread().getName() + "第二次设置值:" + mainUser);
        threadPool.execute(() -> {
            String s1 = userThreadLocal.get();
            // 这里会获取到值,TransmittableThreadLocal做了包装处理，每次调用任务的时，都会将当前的主线程的TTL数据copy到子线程里面
            System.out.println("当前线程：" + Thread.currentThread().getName() + "第二次获取值:" + s1);
        });
        threadPool.shutdown();
    }
}
