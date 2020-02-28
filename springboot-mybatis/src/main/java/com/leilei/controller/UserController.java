package com.leilei.controller;

import com.leilei.entity.User;
import com.leilei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:26 2020/2/24
 * @desc :
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("findAll")
    public List<User> findAll() {
        return userService.selectAll();
    }

    /**
     * 批量新增
     *
     * @param userList
     * @return
     */
    @PostMapping("insertMore")
    public Integer insertMore(List<User> userList) {
        return userService.insertMore(userList);
    }

    /**
     *
     * @param user
     * @return 新增  测试获取主键
     */
    @PostMapping("insert")
    public Integer insert(User user) {
                userService.insert(user);
        return user.getId().intValue();
    }

    @PostMapping("rmMore")
    public Integer RemoveMore(List<Long> ids) {
        return userService.rmMore(ids);
    }
}
