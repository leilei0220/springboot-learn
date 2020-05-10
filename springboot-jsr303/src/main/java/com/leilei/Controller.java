package com.leilei;

import com.leilei.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @date 2020/5/10 17:10
 * @desc
 */
@RestController
public class Controller {

  @RequestMapping("/test")
  public User test(@Validated User user) {
    return user;
  }

}
