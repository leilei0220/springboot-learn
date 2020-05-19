package com.leilei.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @date 2020/5/19 17:15
 * @desc 我这里用static 修饰 并去除set 的static关键字 获取属性直接类名.方法名即可  不用再注入了(
 */
@PropertySource("classpath:leilei.properties")
@ConfigurationProperties(prefix = "jdbc")
@Component
public class MyProperties {

  private static String username;
  private static String password;

  public static String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    MyProperties.username = username;
  }

  public static String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    MyProperties.password = password;
  }
}
