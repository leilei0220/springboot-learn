package com.leilei.controller;


import com.leilei.entity.User;
import com.leilei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author leilei
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;


    @RequestMapping("findOneByRole/{userId}")
    public User findOneByRole(@PathVariable("userId") Long  userId) {
        User user = userService.selectOneDetail(userId);
        System.out.println(user);
        return user;
    }

    /**
     * 注解方式连表查询 用户 房子 一对多
     * @param userid
     * @return
     */
    @RequestMapping("findOne/{userId}")
    public User findOne(@PathVariable("userId") Long  userid) {
        User user = userService.selectOne(userid);
        System.out.println(user);
        return user;
    }

    @RequestMapping("findOneByReal")
    public User findOneByReal(Long  userid) {
        User user = userService.selectOneDetailByReal(userid);
        System.out.println(user);
        return user;
    }
}

