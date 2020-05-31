package com.leilei.controller;

import com.leilei.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Controller
public class ThymeleafController {

    /**
     * 简单使用
     *
     * @param model
     * @return
     */
    @RequestMapping({"/", "/index"})
    private String easy(Model model) {
        model.addAttribute("name", "hello-thymeleaf");
        return "index";
    }

    /**
     * 对象取值
     *
     * @param model
     * @return
     */
    @RequestMapping("/user")
    private String obj(Model model) {
        User user = new User("张三,又叫小三三", 12, false, LocalDateTime.now());
        model.addAttribute("user", user);
        return "user";
    }

    /**
     * 高级用法 循环等
     */
    @RequestMapping("super")
    public String superMethod(Model model) {
        User kaiduo = new User("凯多", 45, false, LocalDateTime.now());

        List<User> users = Arrays.asList(new User("憨憨", 13, true, LocalDateTime.now()),
                new User("二柱子", 12, false, LocalDateTime.now()),
                new User("卡卡", 32, false, LocalDateTime.now()),
                kaiduo);
        model.addAttribute("users", users);
        model.addAttribute("role", kaiduo);
        return "super";
    }

    /**
     * 使用 model js中也能获取到值
     * 此应用场景 可用于 一些 echarts 等报表
     */
    @RequestMapping("/chart")
    public String echarts(Model model) {
        model.addAttribute("dayCount", 1);
        model.addAttribute("weekCount", 5);
        model.addAttribute("monthCount", 10);
        model.addAttribute("quarterCount", 30);
        model.addAttribute("yearCount", 60);
        model.addAttribute("totalCount", 130);
        return "chart";
    }
}
