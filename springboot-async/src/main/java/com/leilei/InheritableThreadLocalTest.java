package com.leilei;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lei
 * @create 2023-07-05 16:12
 * @desc 可继承的threadLocal
 * 解决了父子线程变量传递问题，但是仅限于子线程每次都新创建的情况，如果子线程复用则达不到父线程变量赋值给子线程的要求
 *  细节可以查看thread 的构造方法，在第一次创建线程时才会做对应处理
 **/
public class InheritableThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> userThreadLocal = new InheritableThreadLocal<>();
        String mainUser = "张三";
        System.out.println("主线程：" + Thread.currentThread().getName() + " 设置值:" + mainUser);
        // ThreadLocal 默认set get 是以当前调用线程,这里set这就是main线程
        userThreadLocal.set(mainUser);
        String s = userThreadLocal.get();
        System.out.println("当前线程：" + Thread.currentThread().getName() + " 获取值:" + s);

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.execute(() -> {
            String s1 = userThreadLocal.get();
            // 这里会获取到值,InheritableThreadLocal在子线程第一次创建时会将当前调用线程变量赋值给子线程 new Thread() 逻辑
            System.out.println("当前线程：" + Thread.currentThread().getName() + "第一次获取值:" + s1);
        });
        Thread.sleep(1000);
        mainUser = "张三第二次设置";
        userThreadLocal.set(mainUser);
        System.out.println("主线程：" + Thread.currentThread().getName() + "第二次设置值:" + mainUser);
        threadPool.execute(() -> {
            String s1 = userThreadLocal.get();
            // 这里会获取到值，但获取的是旧值，因为子线程已经创建过了，这里是复用了子线程（里边信息仍是子线程new时的信息）
            System.out.println("当前线程：" + Thread.currentThread().getName() + "第二次获取值:" + s1);
        });
        threadPool.shutdown();
    }
}
