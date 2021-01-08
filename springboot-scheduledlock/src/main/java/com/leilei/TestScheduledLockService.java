package com.leilei;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * @author lei
 * @version 1.0
 * @date 2020-10-21 17:57
 * @desc
 */
@Component
public class TestScheduledLockService {
    // @Scheduled(cron = "*/5 * * * * ?")
    // @SchedulerLock(name = "test", lockAtMostFor = "10", lockAtLeastFor = "10")
    // public void test1()  {
    //     System.out.println("当前时间 ----test---1----触发"+new Date());
    // }
    @Scheduled(cron = "*/5 * * * * ?")
    @SchedulerLock(name = "test", lockAtMostFor = "10", lockAtLeastFor = "10")
    public void test2()  {
        System.out.println("当前时间 ----test---2----触发"+new Date());
    }
}
