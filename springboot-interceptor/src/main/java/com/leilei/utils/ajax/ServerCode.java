package com.leilei.utils.ajax;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/05/08 19:35
 * @desc: ajax数据返回通用服务器状态码 全局常量
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
   * 未授权
   */
  public static final int AUTH_FAILURE = 401;

  /**
   * 未找到资源
   */
  public static final int NOT_FOUND = 404;

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
  public static final Integer NEED_PAY = 9999;
}
