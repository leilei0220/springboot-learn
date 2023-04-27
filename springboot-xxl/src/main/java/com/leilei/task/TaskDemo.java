package com.leilei.task;

import com.leilei.service.TestService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
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

    // @XxlJob("leiTask")
    // public ReturnT<String> myFirstTask(String param) throws Exception {
    //     XxlJobLogger.log("myFirstTsk");
    //     String test = testService.test();
    //     System.out.println(test);
    //     System.out.println(LocalDateTime.now()+param);
    //     return ReturnT.SUCCESS;
    // }


    @XxlJob("leiTask")
    public ReturnT<String> myFirstTask() {
        String param = XxlJobHelper.getJobParam();
        String s = LocalDateTime.now() + param;
        System.out.println(s);
        XxlJobHelper.log("myFirstTsk" + s);
        return ReturnT.SUCCESS;
    }

    /**
     * 分片任务
     *
     * @param
     * @return ReturnT<String>
     * @author lei
     * @date 2023-04-27 10:31:31
     */
    @XxlJob("shardingTask")
    public void shardingTask() {
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        String param = XxlJobHelper.getJobParam();
        String log = String.format("当前时间%s 触发定时任务,总任务片数:%d,当前片:%d,执行参数为：%s", LocalDateTime.now(), shardTotal, shardIndex + 1, param);
        System.out.println(log);
        XxlJobHelper.log(log);
        XxlJobHelper.handleSuccess("分片任务执行成功");
    }


}
