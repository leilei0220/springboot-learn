package com.leilei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @date 2020/5/9 18:26
 * @desc
 */
@RequestMapping("/test")
@RestController
public class TestController {

  @PostMapping("/insert")
  public String postMethod(String name, Integer age) {
    return "post 请求--姓名：" + name + "年龄：" + age;
  }

  @GetMapping("/getInfo")
  public String getMethod(String name) {
    return "get 请求，我传入的姓名是：" + name;
  }
}
