package com.leilei.controller;

import com.leilei.common.AjaxResult;
import com.leilei.entity.User;
import com.leilei.entity.ValidResultList;
import com.leilei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @date 2020/5/10 17:10
 * @desc
 */
@RestController
public class UserController {
  private final UserService userService;
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/addUser")
  public AjaxResult add(@Validated @RequestBody User user) {
    return userService.addUser(user);
  }
  @RequestMapping("/addUser/batch")
  public AjaxResult addBatch(@RequestBody @Validated ValidResultList<User> users) {
    return userService.addUser(users.get(0));
  }

}
