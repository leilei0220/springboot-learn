package com.leilei.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : leilei
 * @date : 18:22 2020/2/19
 * @desc :
 */
@RestController
@RequestMapping("profile")
public class TestProfileController {
    @Value("${server.port}")
    private Integer port;
    @GetMapping("/port")
    public Integer getPort() {
        return port;
    }
}
