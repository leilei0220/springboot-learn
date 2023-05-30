package com.leilei;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author lei
 * @create 2023-05-30 10:02
 * @desc 测试接口
 **/
@RequestMapping("hello")
@RestController
@Log4j2
public class HelloController {

    @GetMapping
    public String helloWorld() throws InterruptedException {
        log.info("当前处理线程:{}", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return "helloWorld";
    }
}
