package com.leilei.util.response;

/**
 * 服务端返回码 Created by leilei on 2019/8/28.
 */
public class ServerCode {

  /**
   * 成功
   */
  public static final int SUCCESS = 200;

  /**
   * 默认状态
   */
  public static final int DEFAULT = SUCCESS;

  /**
   * 失败
   */
  public static final int FAILURE = -1;

  /**
   * 错误
   */
  public static final int ERROR = -2;

  /**
   * 登录失效
   */
  public static final int EXPIRE = 1000;

  /**
   * 需要支付
   */
  public static final Integer NEED_PAY = 8100;
}
