package com.leilei.controller;

import com.leilei.service.BizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2023-01-09 17:19
 * @desc
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    private final BizService bizService;

    public TestController(BizService bizService) {
        this.bizService = bizService;
    }

    @GetMapping("/thread")

    public String testThread() {
        bizService.execute();
        return "ok";
    }
}
