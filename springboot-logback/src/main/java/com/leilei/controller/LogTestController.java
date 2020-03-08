package com.leilei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : leilei
 * @date : 17:22 2020/3/8
 * @desc :
 */
@RestController
@RequestMapping("log")
@Slf4j
public class LogTestController {

    @RequestMapping("test")
    public Integer test(Integer id) {
        log.info("info日志：【{}】", id);
        log.warn("warn日志：【{}】", id);
        log.error("error日志：【{}】", id);
        return id;
    }
}
