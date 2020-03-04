package com.leilei.controller;


import com.leilei.entity.User;
import com.leilei.service.IRealEseateService;
import com.leilei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author leilei
 * @since 2020-03-02
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("findOneByRole")
    @ResponseBody
    public User findOneByRole(Long  userid) {
        User user = userService.selectOneDetail(userid);
        System.out.println(user);
        return user;
    }


    @PostMapping("findOneByReal")
    @ResponseBody
    public User findOneByReal(Long  userid) {
        User user = userService.selectOneDetailByReal(userid);
        System.out.println(user);
        return user;
    }
}

