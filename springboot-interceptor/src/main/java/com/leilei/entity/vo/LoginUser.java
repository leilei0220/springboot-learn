package com.leilei.entity.vo;

import com.leilei.entity.User;
import java.io.Serializable;
import lombok.Data;

/**
 * @author lei
 * @date 2020/5/8 19:48
 * @desc 登陆后的用户信息
 */
@Data
public class LoginUser implements Serializable {

  /**
   * 用户唯一标识
   */
  private String token;

  /**
   * 登陆时间 时间戳形式
   */
  private Long loginTime;

  /**
   * 用户信息
   */
  private User user;
}
