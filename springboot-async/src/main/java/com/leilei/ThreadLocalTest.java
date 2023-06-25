package com.leilei;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lei
 * @create 2023-06-25 17:02
 * @desc threadLocal线程变量使用与踩坑
 * 优点 可以线程与线程之间数据隔离
 * 缺点： 父子线程之间无法值共享，比如开启新线程后，就无法获取调用者线程的信息了，在某些异步场景下数据可能丢失
 **/
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> userThreadLocal = new ThreadLocal<>();
        String mainUser = "张三";
        System.out.println("当前线程：" + Thread.currentThread().getName() + " 设置值:" + mainUser);
        // ThreadLocal 默认set get 是以当前调用线程,这里set这就是main线程
        userThreadLocal.set(mainUser);
        String s = userThreadLocal.get();
        System.out.println("当前线程：" + Thread.currentThread().getName() + " 获取值:" + s);

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(() -> {
            String s1 = userThreadLocal.get();
            // 这里不会获取到值，因为设置线程与获取线程不是同一个，设置线程是main,获取线程是我们线程池中的线程
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 获取值:" + s1);
            //---------------------------------------------------------------------------
            userThreadLocal.set("小马哥");
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 设置值:" + mainUser);
            //这里可以获取到值,因为set get调用者为同一个线程
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 获取值:" + userThreadLocal.get());
        });
        threadPool.shutdown();
    }
}
