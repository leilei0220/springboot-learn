package com.leilei.entity.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @date 2020/5/8 20:34
 * @desc token 常量
 */
@ConfigurationProperties(prefix = "token")
@Component
public class TokenConstant {

  private static String header;

  public static String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    TokenConstant.header = header;
  }


}
