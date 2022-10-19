package com.leilei.controller;

import com.alibaba.fastjson.JSON;
import com.leilei.common.AjaxResult;
import com.leilei.entity.User;
import com.leilei.jwt.AccPermission;
import com.leilei.jwt.JwtSupport;
import com.leilei.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 16:27
 * @desc
 */
@RestController
public class TestController {
    private final LoginService loginService;
    private final JwtSupport jwtSupport;

    @Autowired
    public TestController(LoginService loginService, JwtSupport jwtSupport) {
        this.loginService = loginService;
        this.jwtSupport = jwtSupport;
    }

    @PostMapping("/login")
    public AjaxResult<String> login(@RequestBody User user) {
        return loginService.login(user);
    }

    @PostMapping("/test")
    @AccPermission
    public AjaxResult<String> test(String param, @RequestHeader("Authorization") String token) {
        return AjaxResult.success("param:" + param + "      ====    userInfo" +
                JSON.toJSONString(jwtSupport.parseJwt(token)));
    }
}
