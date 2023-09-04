package com.leilei.config;

import com.leilei.entity.Location;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lei
 * @create 2023-08-29 17:30
 * @desc
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Location location = new Location();
        location.setPlate("川A");
        location.setColor("黄");
        location.setNum(0);
        AtomicReference<Location> reference = new AtomicReference<>(location);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.execute(()-> {
            Location old = reference.get();
            Location now = new Location();
            location.setPlate("川A");
            location.setColor("黄");
            now.setNum(old.getNum() + 1);
            reference.compareAndSet(old, now);
        });
        System.out.println(reference.get().getNum());
    }
}
