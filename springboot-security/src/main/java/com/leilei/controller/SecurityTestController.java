package com.leilei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lei
 * @version 1.0
 * @date 2020/6/3 21:55
 */
@Controller
public class SecurityTestController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "/user/index";
    }
    @RequestMapping("/user/add")
    public String add() {
        return "/user/add";
    }
    @RequestMapping("/user/edit")
    public String edit() {
        return "/user/edit";
    }
}
