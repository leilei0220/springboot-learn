package com.leilei.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/8 21:36
 * @desc Restful 风格
 */
@RestController
@RequestMapping("/user")
public class RestfulController {
    @PostMapping
    public String post(@RequestBody User user) {
        return "POST请求--新增"+user.toString();
    }
    @PutMapping
    public String put(@RequestBody User user) {
        return "PUT请求--修改"+user.toString();
    }
    @GetMapping
    public String get() {
        return "GET请求";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        return "DELETE请求"+id;
    }

    @GetMapping("/info")
    public String info() {
        return "额外info";
    }
}
