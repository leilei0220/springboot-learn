package com.leilei.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei
 * @date 2020/5/8 19:44
 * @desc 用户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
  /**用户账户*/
  private String account;
  /**用户密码*/
  private String password;
}
