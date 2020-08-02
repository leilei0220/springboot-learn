package com.leilei.task;

import com.leilei.service.TestService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/1 23:34
 * @desc
 */
@Component
public class TaskDemo {
    @Autowired
    private TestService testService;
    @XxlJob("leiTask")
    public ReturnT<String> myFirstTask(String param) throws Exception {
        XxlJobLogger.log("myFirstTsk");
        String test = testService.test();
        System.out.println(test);
        System.out.println(LocalDateTime.now()+param);
        return ReturnT.SUCCESS;
    }


}
