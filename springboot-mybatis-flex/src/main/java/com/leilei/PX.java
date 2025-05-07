package com.leilei;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author: lei
 * @date: 2025-03-27 17:51
 * @desc:     -javaagent:"D:\Program Files (x86)\repository\com\alibaba\transmittable-thread-local\2.14.2\transmittable-thread-local-2.14.2.jar"
 */
public class PX {
    public static void main(String[] args) throws InterruptedException {
        TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();
        threadLocal.set("value-set-in-parent");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + threadLocal.get());
            });
        }
        Thread.sleep(3000);
        threadLocal.set("value-set-invalid222");
        System.out.println("0----------");

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + threadLocal.get());
            });
        }

        threadLocal.set("value-set-invalid333");
        System.out.println("0----------");

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + threadLocal.get());
            });
        }
        threadLocal.remove();
        System.out.println("0----------");

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + threadLocal.get());
            });
        }
        executorService.shutdownNow();
    }
}
