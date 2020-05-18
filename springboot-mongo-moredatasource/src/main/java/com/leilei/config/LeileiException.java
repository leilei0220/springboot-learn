package com.leilei.config;


/**
 * @author lei
 * @date 2020/5/18 17:28
 * @desc
 */

public class LeileiException extends Exception {

  private String msg;

  public LeileiException() {
    super();
  }
  public LeileiException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
