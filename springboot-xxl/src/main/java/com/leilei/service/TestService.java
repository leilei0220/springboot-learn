package com.leilei.service;

import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/2 9:23
 * @desc
 */
@Service
public class TestService {
    public String test() {
        return "测试自动注入";
    }
}
