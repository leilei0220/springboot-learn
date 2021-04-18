package com.leilei.controller;

import com.leilei.entity.User;
import com.leilei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 15:00
 * @desc
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    private User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/{id}")
    private Boolean deleteById(@PathVariable("id") Integer id) {
        return userService.deleteById(id);
    }

    @PostMapping
    private User findUserById(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    private User modifyUser(@RequestBody User user) {
        return userService.modifyUser(user);
    }

    /**
     * 模拟根据用户创建者查询所有
     * @return
     */
    @GetMapping("/all/{userId}")
    private List<User> findAll(@PathVariable("userId") String userId) {
        //todo 假设是从Jwt中获取到的用户信息
        return userService.findAll(userId);
    }

}
