package com.leilei.controller;

import com.leilei.entity.two.User;
import com.leilei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : leilei
 * @date : 17:10 2020/3/1
 * @desc :
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     *
     * @param page
     * @param size
     * @return 分页测试
     */
    @RequestMapping("findall")
    public List<User> findall(Integer page, Integer size) {
        return userService.selectAll(page,size);
    }

    @PostMapping("insert")
    public Long insert(User user) {
        userService.insert(user);
        return user.getId();
    }
}
