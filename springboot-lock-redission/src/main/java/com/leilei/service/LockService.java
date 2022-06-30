package com.leilei.service;

import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lei
 * @create 2021-09-09 15:41
 * @desc 商品测试类
 **/
@Service
@Log4j2
public class LockService {
    @Resource
    private final RedissonClient redissonClient;

    public LockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    RRateLimiter rateLimiter;

    /**
     * 可重入非公平锁
     * 获取锁的顺序 随机
     */
    public void reentrantUnFairLock() {
        //普通非公平重入锁 不根据请求先后顺序,随机线程获取锁
        RLock lock = redissonClient.getLock("reentrant-lock");
        lockDemo(lock);
    }

    /**
     * 可重入公平锁
     * 根据线程尝试获取顺序 给锁，先到先得
     */
    public void reentrantFairLock() {
        RLock lock = redissonClient.getFairLock("reentrant-fair-lock");
        lockDemo(lock);
    }

    /**
     * 可重入 公平锁 与非 公平锁特性
     * 特性
     * 可重入的（使用hash数据结果保存，其中hash key是client id，value是重入次数）
     * 不间断的（默认锁的到期时间是30秒，如果没有释放，则当到期时间为20秒时，再延长至30秒）
     * @param lock
     */
    private void lockDemo(RLock lock) {
        System.out.println("当前线程:" + Thread.currentThread().getName());
        //加锁
        lock.lock();
        try {
            System.out.println("线程:" + Thread.currentThread().getName() + "获取到锁!");
            Thread.sleep(4000_0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //判断 拿到了锁的才释放锁,否则会报错!
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                System.out.println("线程:" + Thread.currentThread().getName() + "释放锁!");
                lock.unlock();
            }
        }
        System.out.println("线程:" + Thread.currentThread().getName() + "业务结束!");
    }


    /**
     * 模拟多个线程抢锁 抢到的执行，未抢到的放弃
     */
    public String threadGrabLock() {
        RLock lock = redissonClient.getFairLock("grab-fair-lock");
        System.out.println("当前线程:" + Thread.currentThread().getName());
        //加锁
        try {
            if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                String message = "线程:" + Thread.currentThread().getName() + "未获取到锁,直接返回";
                System.out.println(message);
                return message;
            }
            System.out.println("线程:" + Thread.currentThread().getName() + "获取到锁!");
            Thread.sleep(4000_0);
            System.out.println("线程:" + Thread.currentThread().getName() + "业务结束!");
        } catch (InterruptedException e) {
            throw new RuntimeException(String.format("出现异常：%s",e.getMessage()));
        } finally {
            //判断 拿到了锁的才释放锁,否则会报错!
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                System.out.println("线程:" + Thread.currentThread().getName() + "释放锁!");
                lock.unlock();
            }
        }
        return "线程:" + Thread.currentThread().getName() + "业务结束!";
    }

    @PostConstruct
    public void initRateLimiter(){
        RRateLimiter ra = redissonClient.getRateLimiter("rate-limiter");

        // setRate 每次服务重启就会强制更新之前的限流配置（也重置了限流状态）,以当前为准
        ra.setRate(RateType.OVERALL, 6, 1, RateIntervalUnit.MINUTES);

        // trySetRate 服务重启不会更新限流配置与限流状态,但参数更改后亦不会生效!
        // ra.trySetRate(RateType.OVERALL, 7, 2, RateIntervalUnit.MINUTES);
        rateLimiter = ra;
    }

    public String testRateLimiter() {
        boolean b = rateLimiter.tryAcquire();
        if (b) {
            return "ok";
        }
        return "fail";
    }
}
